package com.rfli.springbootrf.Util;

import com.alibaba.fastjson2.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class merkleTree {

    public String root = null;  //merkle tree root hash

    /**
     * @param cap input value
     * @return Returns the nearest power of 2 to cap
     */
    public static final int tableSizeFor(int cap) {
        // The threshold for capacity expansion is the nearest 2 to the nth power of the incoming initial capacity
        // prevent itself from being a power of 2
        int n = cap - 1;
        // unsigned right shift
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    /**
     * @param valid_file Read the json string of a valid transaction to generate a merkle tree
     * @param early      earliest timestamp
     * @param late       latest timestamp
     * @return The merkle path json string of the transaction
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */

    public String generation(String valid_file, String early, String late) throws IOException, NoSuchAlgorithmException {
        JSONObject jsonObject = JSONObject.parseObject(valid_file, JSONObject.class);
        //number of transactions
        int size = jsonObject.size();
        //Calculate the total number of nodes at the bottom of the tree
        int low_nodes = tableSizeFor(size);
        //calculate tree height
        //Tree height counts from 0
        int h = Integer.toBinaryString(low_nodes).length() - 1;
        //Calculate the total number of nodes in the tree
        int treenodes = (int) (Math.pow(2, h + 1) - 1);
        //number of random nodes
        int random_num = low_nodes - size;
        int begin = (int) Math.pow(2, h);
        int end = (int) Math.pow(2, h + 1) - 1;

        //Construct a list of merkle trees
        List<String> mt = new ArrayList<>(treenodes);
        //node path node1/node2/.../noden
        List<String> path = new ArrayList<>(treenodes);
        //Mapping from node number to txid in the tree
        Map<Integer, String> mp = new HashMap<Integer, String>();
        for (int i = 0; i <= treenodes; i++) {
            mt.add("0");
            path.add("0");
        }
        //Calculate the lowest node value
        int index = begin;
        for (String str : jsonObject.keySet()) {
            JSONObject tmp = JSONObject.parseObject(jsonObject.get(str).toString(), JSONObject.class);
            //Get the cm value in the transaction
            String cm = tmp.get("cm").toString();

            mt.set(index, cm);
            path.set(index, Integer.toString(index));
            mp.put(index, str);
            index++;
        }
        for (int i = index; i <= end; i++) {
            SecureRandom sr = new SecureRandom();
            mt.set(i, new BigInteger(256, sr).toString());
        }

        //Merkle node value is calculated from bottom to top
        int tmp_h = h - 1;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        while (tmp_h >= 0) {
            int b_index = (int) (Math.pow(2, tmp_h));
            int e_index = (int) (Math.pow(2, tmp_h + 1) - 1);
            for (int i = b_index; i <= e_index; i++) {
                md.reset();
                int lchild = i * 2;
                int rchild = i * 2 + 1;
                md.update(mt.get(lchild).getBytes());
                md.update(mt.get(rchild).getBytes());
                byte[] digest = md.digest();
                BigInteger tmp = new BigInteger(1, digest);
                mt.set(i, tmp.toString());
            }
            tmp_h--;
        }
        JSONObject pathjson = new JSONObject();
        //Calculate merkle node path
        for (int i = begin; i < begin + size; i++) {
            int topindex = i;
            while (topindex > 1) {
                int iseven = 0;   //Whether it is an even number, 1 represents even number.
                if (topindex % 2 == 0) iseven = 1;
                if (iseven == 1) {
                    String s = path.get(i);
                    s = s + "/" + Integer.toString(topindex + 1);
                    path.set(i, s);
                } else {
                    String s = path.get(i);
                    s = s + "/" + Integer.toString(topindex - 1);
                    path.set(i, s);
                }
                topindex /= 2;
            }
            pathjson.put(mp.get(i), path.get(i));
        }
        //Write merkle tree to json file
        JSONObject mtjson = new JSONObject();
        JSONObject nodejson = new JSONObject();
        for (int i = 1; i <= treenodes; i++) {
            nodejson.clear();
            if (mp.containsKey(i)) {
                nodejson.put("txid", mp.get(i));
                nodejson.put("path", path.get(i));
            } else {
                nodejson.put("txid", "0");
                nodejson.put("path", "0");
            }
            nodejson.put("cm", mt.get(i));
            mtjson.put(Integer.toString(i), nodejson.toString());
        }
        //write operation
        BufferedWriter bw = null;
        String filename = "./data/merkleTree/mt-";
        filename = filename + early + "-" + late + ".json";
        bw = new BufferedWriter(new FileWriter(filename));
        bw.write(mtjson.toString());//转化成字符串再写
        bw.close();
        this.root = mt.get(0);
        return pathjson.toString();
    }

    /**
     * Verify whether the transaction file is legal in merkle
     *
     * @param trans_file transaction file path
     * @param mk_path    Merkle tree file path
     * @return true/false  if the Verification passed return true, otherwise return false
     */
    public static boolean verify(String trans_file, String mk_path) throws IOException, NoSuchAlgorithmException {
        //The json structure of the transaction
        JSONObject transjson = JSONObject.parseObject(new String(new FileInputStream(trans_file).readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);
        //The json structure of the merkle tree
        JSONObject mtjson = JSONObject.parseObject(new String(new FileInputStream(mk_path).readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        //get transaction information
        String t_txid = transjson.get("txid").toString();
        String t_cm = transjson.get("cm").toString();
        //Locate which node in the tree the transaction is in
        String lid = null;
        //merkle root hash
        String root = null;
        for (String str : mtjson.keySet()) {
            JSONObject singlenode = JSONObject.parseObject(mtjson.get(str).toString(), JSONObject.class);
            if (str.equals("1")) root = singlenode.get("cm").toString();
            String txid = singlenode.get("txid").toString();
            if (txid.equals(t_txid)) {
                lid = str;
                break;
            }
        }
        if (lid == null) return false;
        //Extract information about the node
        JSONObject node = JSONObject.parseObject(mtjson.get(lid).toString(), JSONObject.class);
        String path = node.get("path").toString();
        //remove its own node
        int ti = 0;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                ti = i + 1;
                break;
            }
        }
        path = path.substring(ti);
        String[] node_list = path.split("/");
        //verification  left part hash hash1||right part hash2, Calculate step by step according to the merkle path
        String hash1 = null, hash2 = null, ret = null;
        for (int i = 0; i < node_list.length; i++) {
            JSONObject singlenode = JSONObject.parseObject(mtjson.get(node_list[i]).toString(), JSONObject.class);
            if (i == 0) {
                if (Integer.parseInt(node_list[i]) % 2 == 0) {
                    hash1 = singlenode.get("cm").toString();
                    hash2 = t_cm;
                } else {
                    hash2 = singlenode.get("cm").toString();
                    hash1 = t_cm;
                }
            } else {
                if (Integer.parseInt(node_list[i]) % 2 == 0) {
                    hash1 = singlenode.get("cm").toString();
                    hash2 = ret;
                } else {
                    hash2 = singlenode.get("cm").toString();
                    hash1 = ret;
                }
            }
            md.reset();
            md.update(hash1.getBytes());
            md.update(hash2.getBytes());
            byte[] digest = md.digest();
            BigInteger tmp = new BigInteger(1, digest);
            ret = tmp.toString();
        }
        //Is the final hash value equal to the root hash of the merkle tree
        return ret.equals(root);
    }
}
