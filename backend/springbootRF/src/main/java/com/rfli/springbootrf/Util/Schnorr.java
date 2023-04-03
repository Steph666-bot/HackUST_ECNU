package com.rfli.springbootrf.Util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Schnorr {
    //q为160bit
//    static public BigInteger global_p = new BigInteger("240761876412092678553379779094576250223119074028579");
//    static public BigInteger global_q = new BigInteger("878692979606177658953940799615241789135471073097");
//    static public BigInteger global_a = new BigInteger("209815926530930717396754761966274762723399759979480");
    //q为1024bit
    static public BigInteger global_p = new BigInteger("7386746550518142738224863349584023536983602629742175342000622786873120467645932285672223188766837249561526701689455967725718749799725450545497214753713499577334669212812831494769340791706791005568211659320246706532355845238632806956456488127351329675634780802161257810763412466127447438189828150467846698053907");
    static public BigInteger global_q = new BigInteger("94701878852796701772113632686974660730559008073617632589751574190681031636486311354772092163677400635404188483198153432381009612816992955711502753252737174068393195036061942240632574252651166738053995632310855211953280067161959063544313950350658072764548471822580228343120672642659582540895232698305726898127");
    static public BigInteger global_a = new BigInteger("56306607101535945924686105463666469983043416245374595538792682620089408399533321483450737187281070507863864723609993343369899645476039812212056259454984601851245119272731270776640893375018835321469048430262222724520957526525921689103363891264510895842964404543492077516060035877194815380812678223492209487320");

    /**
     * Generate global parameters p, q, and a.
     *
     * @param blq: bit length of the selected q.
     */
    public static void initPara(int blq) {
        System.out.println("During system initialization, generate public parameters.... ...");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger q, qp, p, a, g;
        int certainty = 100;
        SecureRandom sr = new SecureRandom();
        // blq-length q, where q is a prime factor of p-1.
        //生成BigInteger伪随机数，它可能是（概率不小于1 - 1/2certainty）一个具有指定 bitLength 的素数
        q = new BigInteger(blq, certainty, sr);
        qp = BigInteger.ONE;
        do { // Select a prime number p.
            p = q.multiply(qp).multiply(two).add(one);
            if (p.isProbablePrime(certainty))
                break;
            qp = qp.add(BigInteger.ONE);
        } while (true);

        while (true) {
            a = (two.add(new BigInteger(blq, 100, sr))).mod(p);// (2+x) mod p
            BigInteger ga = (p.subtract(BigInteger.ONE)).divide(q);// (p-1)/q
            g = a.modPow(ga, p); // a^ga mod p = 1
            if (g.compareTo(BigInteger.ONE) != 0) // g!=1
                break;
        }
        // Store the public parameters.
        System.out.println("...");
        System.out.println("Initialization complete.！");
        System.out.println("p:" + p);
        System.out.println("q:" + q);
        System.out.println("a:" + g);


    }

    /**
     * Generate a public-private key pair.
     * @return pk/sk retruna public-private key pair. '/' is the delimiter.
     */
    public static String generateKey() {
//        BigInteger p = new BigInteger("118239383441760904284380137214195022189308692185853");
//        BigInteger q = new BigInteger("1407611707640010765290239728740416930825103478403");
//        BigInteger a = new BigInteger("1426378043003897860044628766693213394462830395233");

        BigInteger sk, pk;// private key and public key
        SecureRandom sr = new SecureRandom();
        // random number as private key
        sk = new BigInteger(global_q.bitLength(), sr);
        sk = sk.mod(global_q);
        BigInteger m = new BigInteger("-1");
        // calculate the pk
        pk = global_a.modPow(sk.multiply(m), global_p);//a^(-sk)mod p
//        System.out.println(sk);
//        System.out.println(pk);

        String str = "";
        str += pk.toString();
        str += "/";
        str += sk.toString();
        return str;

    }

    /**
     * calculate schnorr signature
     * @param mess      signed message
     * @param secretKey private key
     * @return string e/y  return signature
     */
    public static String makeSign(String mess, String secretKey) throws Exception {
        System.out.println("makeSign");
        SecureRandom sr = new SecureRandom();
        BigInteger r;
        r = new BigInteger(global_q.bitLength(), sr);
        BigInteger sk = new BigInteger(secretKey);
        r = r.mod(global_q);

        BigInteger x = global_a.modPow(r, global_p);//x=a^r mod p

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mess.getBytes());
        md.update(x.toString().getBytes());
        byte[] digest = md.digest();
        //calculate e
        BigInteger e = new BigInteger(1, digest);
        //calculate y
        BigInteger y = (r.add(sk.multiply(e))).mod(global_q);//y=(r+se) mod q

        String str = "";
        str += e.toString();
        str += "/";
        str += y.toString();
        return str;
    }

    /**
     * verify the signature
     * @param signature signature
     * @param publicKey public key
     * @param mess      signed message
     * @return true/false   if the Verification passed return true, otherwise return false
     */

    public static boolean checkSign(String signature, String publicKey, String mess) throws Exception {
        System.out.println("checkSign");
        String temp_e, temp_y;
        int index = signature.indexOf("/");
        temp_e = signature.substring(0, index);
        temp_y = signature.substring(index + 1);
        BigInteger e = new BigInteger(temp_e);
        BigInteger y = new BigInteger(temp_y);

        BigInteger pk = new BigInteger(publicKey);
        BigInteger temp_x;
        BigInteger ay = global_a.modPow(y, global_p);
        BigInteger pke = pk.modPow(e, global_p).mod(global_p);
        temp_x = ay.multiply(pke).mod(global_p);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mess.getBytes());
        md.update(temp_x.toString().getBytes());
        byte[] digest = md.digest();
        BigInteger hash = new BigInteger(1, digest);
        return e.equals(hash);
    }


}

