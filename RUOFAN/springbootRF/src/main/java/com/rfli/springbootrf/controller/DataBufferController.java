package com.rfli.springbootrf.controller;

import com.alibaba.fastjson2.JSONObject;
import com.rfli.springbootrf.Util.*;
import com.rfli.springbootrf.entity.DocEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin
public class DataBufferController {
    private List<Transaction> trans = new ArrayList<>();
    private List<Transaction> validTrans = new ArrayList<>();
    private List<Transaction> inValidTrans = new ArrayList<>();

    /**
     * receive client's submit and generate transactions
     *
     * @param userSubmit user's submitted data, which includes username, nonce and doc_identifier
     * @return
     */
    @PostMapping("/client/submit")
    public Result userSubmit(@RequestBody UserSubmit userSubmit) throws Exception {

        JSONObject users = JSONObject.parseObject(new String(new FileInputStream("src/main/java/com/rfli/springbootrf/data/user/users.json").readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);
        //get the client's data
        JSONObject user = (JSONObject) users.get(userSubmit.getPk());

        String pk = (String) user.get("pk");
        String sk = (String) user.get("sk");

        //generate transaction
        TransGenerator.transGen(pk, sk, userSubmit.getDoc_identifier(), userSubmit.getNonce());

        return Result.succ(true);
    }

    /**
     * TODO: 这个函数没有实现
     * 将用户发送的数据包转换成为一个待验证的交易
     *
     * @param userSubmit 用户发送过来的数据包
     * @return 待验证的交易
     */
    private Transaction geneFromSubmit(@RequestBody UserSubmit userSubmit) {
        return new Transaction();
    }

    /**
     * verify random transactions every hour
     */
    @Scheduled(cron = "0 30 * * * ? ")
    public void verifyTrans() {
        //The file path where all transactions are stored
        String path = "src/main/java/com/rfli/springbootrf/data/transactions";
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            //read all transaction files under the path
            List<Path> fileNames = paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            //verify random 5 transactions
            for (int i = 0; i < 5; i++) {
                int r = (int) (Math.random() * fileNames.size());
                //get random transaction
                String tran = fileNames.get(r).toString();

                JSONObject transJson = JSONObject.parseObject(new String(new FileInputStream(tran).readAllBytes(), StandardCharsets.UTF_8), JSONObject.class);

                BigInteger nonce = new BigInteger(transJson.getString("nonce").toString());

                //search for the merkle tree which contains the chosen random transaction
                File folder = new File("src/main/java/com/rfli/springbootrf/data/merkleTree");
                File[] listOfFiles = folder.listFiles();//读取所有json文件
                String mkTree = "";
                if (listOfFiles.length > 0) {
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            int index = file.getName().substring(3).indexOf("-");
                            BigInteger start = new BigInteger(file.getName().substring(3, index));
                            BigInteger end = new BigInteger(file.getName().substring(index + 4, file.getName().length() - 5));

                            if (nonce.compareTo(start) == 1 || nonce.compareTo(start) == 0) {
                                if (nonce.compareTo(end) == -1 || nonce.compareTo(end) == 0) {
                                    mkTree = file.getName();
                                    break;
                                }
                            }
                        }
                    }
                }
                //verify the transaction
                if (mkTree != null) {
                    System.out.println(merkleTree.verify(tran, "src/main/java/com/rfli/springbootrf/data/merkleTree/" + mkTree));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Combine all transaction files in an hour into a sigle file at the end of each hour
     *
     * @throws Exception
     */
    @Scheduled(cron = "59 59 * * * ?")
    public void combineTrans() throws Exception {

        //get the path of the transaction files
        String path = "src/main/java/com/rfli/springbootrf/data/transactions/";
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");
        String time = dateFormat.format(date).substring(0, 10) + "-" + dateFormat.format(date).substring(12, 14);

        path = path + time;

        //combine all transaction files
        TransGenerator.transCombine(path);

    }
}
