package com.rfli.springbootrf.Util;

import com.alibaba.fastjson2.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class aggreTrans {

    /**
     * Distinguish between valid and invalid transactions, and construct a Merkle tree for the valid transactions.
     *
     * @param filepath File addresses for all transactions within a specific time period.
     * @param adminpk  Administrator's public key.
     * @param adminsk  Administrator's private key.
     * @return
     * @throws Exception
     */

    public static void deal(String filepath,String adminpk,String adminsk) throws Exception {

        adminpk = "4912147900129894172863998822443853339424253825119982129718729856539022666998337535240701800669960250533049891868873790865383678536955047454370175479813156765581884571415562409022549688822364356091299936697046405896038335591408158497707887888594006743825892789071412321617513784398876878055465814775071431536842";
        adminsk = "72340383039916037006695785365787584624781525292095930744826660346478464790239467066911632072104604173349792006869233406214376101014528169285352759451886049186918292369280044049873226012730812197129995782141339331539350313116528943686236527681425151459918612684430796484742463950965613711830367526627659408821";

        //JSON data structure for storing valid transactions.
        JSONObject validjson = new JSONObject();
        //JSON data structure for storing a single transaction.
        JSONObject singlejson = new JSONObject();
        //JSON data structure for storing all transactions.
        JSONObject alljson = JSONObject.parseObject(new String(new FileInputStream(filepath).readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);
        //Click count for each document.
        Map<String, Integer> doc_num = new HashMap<>();
        //JSON data structure for storing click count for each document.
        JSONObject docjson = new JSONObject();

        //Configure a hash algorithm. SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String start = null, end = null;  //Earliest and latest timestamps.
        byte[] digest = md.digest();


        //get field information from a single transaction.
        //Distinguish between valid and invalid transactions.
        String tvalid_list = "", tall_list = "";
        int f = 0;
        for (String str : alljson.keySet()) {
            singlejson = JSONObject.parseObject(alljson.get(str).toString(), JSONObject.class);
            //get signature.
            String sig = singlejson.get("sig").toString();
            //get public key.
            String pk = singlejson.get("pk").toString();
            //get timestamp.
            String nonce = singlejson.get("nonce").toString();
            //get the document id for the clicked file..
            String docj = singlejson.get("docj").toString();
            if (f == 0) {
                start = nonce;
                end = nonce;
                f = 1;
            } else {
                if (nonce.compareTo(start) < 0) start = nonce;
                if (nonce.compareTo(end) > 0) end = nonce;
            }

            //get the message value of the signature.
            md.reset();
            md.update(docj.getBytes());
            md.update(pk.getBytes());
            md.update(nonce.getBytes());
            digest = md.digest();
            BigInteger bi = new BigInteger(1, digest);
            String mess = bi.toString();

            //Verify if the signature is valid to distinguish whether the transaction is valid or not.
            if (Schnorr.checkSign(sig, pk, mess)) {
                tvalid_list = tvalid_list + "/" + str;
                validjson.put(str, singlejson.toString());
                if (doc_num.containsKey(docj)) doc_num.put(docj, doc_num.get(docj) + 1);
                else doc_num.put(docj, 1);
            }
            tall_list = tall_list + "/" + str;
        }
        //List of valid transactions, and list of all transactions.
        String valid_list = tvalid_list.substring(1);
        String all_list = tall_list.substring(1);

        //calculate the Aggregate transaction ID.
        md.reset();
        md.update(adminpk.getBytes());
        md.update(start.getBytes());
        md.update(end.getBytes());
        BigInteger hash = new BigInteger(1, digest);
        String txid = hash.toString();

        //Build a Merkle tree and calculate the Merkle path for valid transactions.
        merkleTree tree = new merkleTree();
        //Store the Merkle path for valid transactions.
        String path = tree.generation(validjson.toString(), start, end);
        //Merkle tree root.
        String root = tree.root;

        //Sign all fields of the aggregate transaction.
        md.reset();
        md.update(valid_list.getBytes());
        md.update(all_list.getBytes());
        md.update(start.getBytes());
        md.update(end.getBytes());
        md.update(root.getBytes());
        md.update(txid.getBytes());
        digest = md.digest();
        hash = new BigInteger(1, digest);
        String sig = Schnorr.makeSign(hash.toString(), adminsk);

        //Store the click count for each file into a JSON data structure.
        for (String s : doc_num.keySet()) {
            docjson.put(s, Integer.toString(doc_num.get(s)));
        }

        //Store corresponding values into the JSON data structure of the aggregate transaction.
        JSONObject arrejson = new JSONObject();
        arrejson.put("txid", txid);
        arrejson.put("root", root);
        arrejson.put("pk", adminpk);
        arrejson.put("sig", sig);
        arrejson.put("validtrans", path);
        arrejson.put("alltrans", all_list);
        arrejson.put("start", start);
        arrejson.put("end", end);
        arrejson.put("result", docjson.toString());

        //Generate the file for the aggregate transaction.
        BufferedWriter bw = null;
        String filename = "./data/aggregation/aggre-";
        filename = filename + start + "-" + end + ".json";
        bw = new BufferedWriter(new FileWriter(filename));
        bw.write(arrejson.toString());//转化成字符串再写
        bw.close();
    }

}
