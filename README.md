## Project Preview

##### 1. Login page

![login](img/login.png)

##### 2. Register page

![register](img/register.png)

##### 3. Vote page

![vote](img/vote.png)

##### 4. Result page![client_result](img/client_result.png)

##### 5. Admin page![admin](img/admin.png)

## Technology stack

**Backend:**

1. SpringBoot
2. fastjson2

**Frontend:**

1. Vue
2. axios
3. vue-router



## Implementation

1. Clone this project to local. 

```git
git@github:https://github.com/Steph666-bot/HackUST_ECNU.git
```

2. Run the project in IntelliJ IDEA. 

3. Go to the vueblog directory and enter the following commands on the command line:

```bash
# Install dependencies
npm install

# Start the project at localhost:8081
npm run server
```

At this point, the project on successful, at this time we directly enter http://localhost:8081 the address bar to access our project. 

## Interfaces

### Class Schnorr

**Class description:** Schnorr signature related parameter generation, signature and verification.

#### class members:

|  member  |    Type    |   Description    |
| :------: | :--------: | :--------------: |
| global_p | BigInteger | Global parameter |
| global_q | BigInteger | Global parameter |
| global_a | BigInteger | Global parameter |


| public static void initPara(int blq) |
| ------------------------------------ |

**Function Description:** Generate global parameters. 
**Input Parameter:**                                  

| Para | Type |         Description          |
| :--: | :--: | :--------------------------: |
| blq  | int  | Bit length of the selected q |

**Output Parameter:** None.                           

| public static String generateKey() |
| ---------------------------------- |

**Function Description:** Generate a public-private key pair. 
 **Input Parameter:** None.                                   
 **Output Parameter:** Return a public-private key pair, the format is pk/sk. 

| public static String makeSign(String mess, String secretKey) |
| ------------------------------------------------------------ |

**Function Description:** Compute schnorr signature.         
**Input Parameter:**                                         

|   Para    |  Type  |  Description   |
| :-------: | :----: | :------------: |
|   mess    | String | Signed message |
| secretKey | String |  Private key   |

**Output Parameter:** Return signature, the format is e/y.   



| public static boolean checkSign(String signature, String publicKey, String mess) |
| ------------------------------------------------------------ |

**Function Description:** Compute schnorr signature.         

**Input Parameter:**

|   Para    |  Type  |  Description   |
| :-------: | :----: | :------------: |
| signature | String |   Signature    |
| publicKey | String |   Public key   |
|   mess    | String | Signed message |

**Output Parameter:** If the Verification passes, then return true, otherwise return false. 

### Class aggreTrans

**Class description:** Aggregate transactions related operations.

| public static void deal(String filepath, String adminpk, String adminsk) |
| ------------------------------------------------------------ |

**Function Description:** Distinguish between valid and invalid transactions, and construct a Merkle tree for the valid transactions. 

 **Input Parameter:**                                         

|   Para   |  Type  |                         Description                          |
| :------: | :----: | :----------------------------------------------------------: |
| filepath | String | File addresses for all transactions within a specific time period |
| adminpk  | String |                  Administrator's public key                  |
| adminsk  | String |                 Administrator's private key                  |

**Output Parameter:** None.                                  

### Class merkleTree

**Class description:** Merkle tree related operations.


**class members:**

| member |  Type  |      Description      |
| :----: | :----: | :-------------------: |
|  root  | String | Merkle tree root hash |



| public static final int tableSizeFor(int cap) |
| --------------------------------------------- |

 **Function Description:** Compute the nearest power of 2 to input. 
 **Input Parameter:**                                         

| Para | Type | Description |
| :--: | :--: | :---------: |
| cap  | int  | Input value |

 **Output Parameter:** Return the nearest power of 2 to cap.  

| public String generation(String valid_file, String early, String late) |
| ------------------------------------------------------------ |

 **Function Description:** Compute the nearest power of 2 to input. 
 
 **Input Parameter:**                                         

|    Para    |  Type  |                         Description                          |
| :--------: | :----: | :----------------------------------------------------------: |
| valid_file | String | Read the json string of a valid transaction to generate a Merkle tree |
|   early    | String |                      Earliest timestamp                      |
|    late    | String |                       Latest timestamp                       |

 **Output Parameter:** Return the Merkle path json string of the transaction. 



| public static boolean verify(String trans_file, String mk_path) |
| ------------------------------------------------------------ |

 **Function Description:** Verify whether the transaction file is valid in Merkle tree. 
 
**Input Parameter:**                                         

|    Para    |
| :--------: |
| trans_file |
|  mk_path   |

**Output Parameter:** If the Verification passes, then return true, otherwise return false. 

