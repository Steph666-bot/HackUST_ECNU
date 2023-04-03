package com.rfli.springbootrf.Util;

import com.alibaba.fastjson2.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransGenerator {

    /**
     * Generate transaction from the client's input.
     *
     * @param pk Client's public key.
     * @param sk  Client's secret key.
     * @param docj  Client's chosen option.
     * @param nonce  timestamp.
     * @throws Exception
     */
    public static void transGen(String pk, String sk, String docj, String nonce) throws Exception {

        //Hash function
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        //Generate transaction id/transaction address: txid=H(pk||nonce)
        md.update(pk.getBytes());
        md.update(nonce.getBytes());

        byte[] digest = md.digest();
        //Convert digest into BigInteger class
        BigInteger tmp = new BigInteger(1, digest);
        //Convert tmp into String class
        String txid = tmp.toString();

        md.reset();

        //Generate client's signature sig=Sig(H(docj||pk||nonce))
        md.update(docj.getBytes());
        md.update(pk.getBytes());
        md.update(nonce.getBytes());
        digest = md.digest();
        tmp = new BigInteger(1, digest);

        String sig = Schnorr.makeSign(tmp.toString(), sk);

        //Generate commitment:cm=H(txid||pk||sig||docj||nonce)
        md.reset();
        md.update(txid.getBytes());
        md.update(pk.getBytes());
        md.update(sig.getBytes());
        md.update(docj.getBytes());
        md.update(nonce.getBytes());
        digest = md.digest();
        tmp = new BigInteger(1, digest);
        String cm = tmp.toString();

        System.out.println("txid:" + txid);
        System.out.println("pk:" + pk);
        System.out.println("docj:" + docj);
        System.out.println("nonce:" + nonce);
        System.out.println("cm:" + cm);

        //The json structure of a single transaction.
        JSONObject jsonObj = new JSONObject();

        //Write data into jsonObj
        jsonObj.put("nonce", nonce);
        jsonObj.put("pk", pk);
        jsonObj.put("sig", sig);
        jsonObj.put("docj", docj);
        jsonObj.put("txid", txid);
        jsonObj.put("cm", cm);


        System.out.println("要添加到JSON文件中的数据:" + jsonObj);

        //Classify transactions according to its generation hour
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        String time = dateFormat.format(date).substring(0, 10) + "-" + dateFormat.format(date).substring(12, 14);

        System.out.println(jsonObj.toString());
        //Create the directory if the directory does not exist.
        String path = "src/main/java/com/rfli/springbootrf/data/transactions/" + time + "/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            //Write into file.
            BufferedWriter bw = null;
            bw = new BufferedWriter(new FileWriter(path + txid + ".json"));
            bw.write(jsonObj.toString());//Convert to String.
            bw.close();

            //Read doc file and increase the number of votes counted.
            JSONObject docs = JSONObject.parseObject(new String(new FileInputStream("src/main/java/com/rfli/springbootrf/data/doc.json").readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);

            JSONObject doc = (JSONObject) docs.get(docj);
            int count = doc.getInteger("count");
            count++;
            doc.replace("count", count);
            docs.replace(docj, doc);

            //write back the data to the file
            BufferedWriter docWriter = new BufferedWriter(new FileWriter("src/main/java/com/rfli/springbootrf/data/doc.json"));
            docWriter.write(docs.toString());
            docWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Combine all transaction files in a period of time into a single file.
     * @param path file path that contains all transaction files
     * @throws Exception
     */
    public static void transCombine(String path) throws Exception {

        //Read all transaction files under the specific path
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        //The final json data that will be written back to the combined transaction file
        JSONObject trans = new JSONObject();

        String minNonce = "";//The nonce of the earliest generated transaction
        String maxNonce = "";//The nonce of the latest generated transaction

        if (listOfFiles != null) {
            //Traverse and read the json file
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    JSONObject jsonObject = JSONObject.parseObject(new String(new FileInputStream(path + "/" + file.getName()).readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);

                    JSONObject tranValue = new JSONObject();

                    //Search for minNonce and maxNonce.
                    if (minNonce.equals("")) {
                        minNonce = jsonObject.get("nonce").toString();
                    } else {
                        if (new BigInteger(jsonObject.get("nonce").toString()).compareTo(new BigInteger(minNonce)) == -1) {
                            minNonce = jsonObject.get("nonce").toString();
                        }
                    }
                    if (maxNonce.equals("")) {
                        maxNonce = jsonObject.get("nonce").toString();
                    } else {
                        if (new BigInteger(jsonObject.get("nonce").toString()).compareTo(new BigInteger(maxNonce)) == 1) {
                            minNonce = jsonObject.get("nonce").toString();
                        }
                    }
                    //Add the transaction to trans, txid as the key.
                    tranValue.put("nonce", jsonObject.get("nonce"));
                    tranValue.put("pk", jsonObject.get("pk"));
                    tranValue.put("sig", jsonObject.get("sig"));
                    tranValue.put("docj", jsonObject.get("docj"));
                    tranValue.put("cm", jsonObject.get("cm"));
                    trans.put(jsonObject.get("txid").toString(), tranValue);
                }
            }

            //Create the directory if the directory does not exist.
            File file = new File(path.substring(0, 17));
            if (!file.exists()) {
                file.mkdirs();
            }
            //write back the combined transactions dato into a single file.
            BufferedWriter bw = null;
            bw = new BufferedWriter(new FileWriter("src/main/java/com/rfli/springbootrf/data/combinedTrans/" + minNonce + "-" + maxNonce + ".json"));
            bw.write(trans.toString());//转化成字符串再写
            bw.close();

            //aggregate the transactions and generate the merkle tree.
            aggreTrans.deal("src/main/java/com/rfli/springbootrf/data/combinedTrans/" + minNonce + "-" + maxNonce + ".json", "", "");
        }


    }

}
