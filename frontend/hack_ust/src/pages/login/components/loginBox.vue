<template>
  <div class="formPage">
    <div class="title">Welcome</div>
    <form>
      <div class="inputBox">
        <div class="label">
          Username：
          <div class="check" :class="emptyUsername ? '' : 'hide'">
            Username cannot be empty
          </div>
        </div>

        <input v-model="username" type="text" />
      </div>

      <div class="inputBox">
        <div class="label">
          Password：
          <div class="check" :class="emptyPassword ? '' : 'hide'">
            Password cannot be empty
          </div>
        </div>
        <input v-model="password" type="password" />
      </div>
      <div class="checkBox">
        <div class="remAcc">
          <div
            class="login"
            @click="getRememberMe()"
            :class="rememberMe ? 'tick' : ' '"
          ></div>
          Remember me
        </div>
        <div class="forgetAcc">Forgot password？</div>
      </div>
      <div>
        <div
          class="check"
          style="margin-top: 10px"
          :class="passwordWrong ? '' : 'hide'"
        >
          Password wrong
        </div>
        <div
          class="check"
          style="margin-top: 10px"
          :class="usernameNotExist ? '' : 'hide'"
        >
          Username not exist
        </div>
      </div>
      <button class="btn" type="submit" @click.prevent="login">Sign in</button>
    </form>
    <div class="register">
      <router-link class="register" to="/register">Register</router-link>
    </div>
  </div>
</template>

<script>
import { useRoute } from "vue-router";
import axios from "axios";

const router = useRoute();
export default {
  data() {
    return {
      username: "", //username
      password: "", //password
      rememberMe: false, // remember password or not
      emptyUsername: false, //judge whether username is empty
      emptyPassword: false, //judge whether password is empty
      passwordWrong: false, //judge whether password is wrong
      usernameNotExist: false, //judge whether username exists
    };
  },
  methods: {
    //login function
    login() {
      //reset parameters
      this.emptyUsername = false;
      this.emptyPassword = false;
      this.passwordWrong = false;
      this.usernameNotExist = false;
      //judge whether username is empty
      if (this.username == null || this.username === "")
        this.emptyUsername = true;
      else this.emptyUsername = false;
      //judge whether password is empty
      if (this.password == null || this.password === "")
        this.emptyPassword = true;
      else this.emptyPassword = false;

      if (!this.emptyUsername && !this.emptyPassword) {
        axios
          .post("http://localhost:8080/login", {
            username: this.username, //data
            password: this.password,
          })
          .then((res) => {
            console.log(res.data);
            if (res.data["data"] === 2) {
              //wrong password
              this.passwordWrong = true;
            } else if (res.data["data"] === 1) {
              //username not found
              this.usernameNotExist = true;
            } else if (res.data["data"] === 0) {
              //client login success
              this.$router.push("/client/" + this.username);
            } else if (res.data["data"] === -1) {
              //admin login success
              this.$router.push("/admin/" + this.username);
            }
          });
      }
    },
    getRememberMe() {
      this.rememberMe = !this.rememberMe; //control remember password check
    },
  },
};
</script>

<style scoped>
.formPage {
  padding-top: 10%;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.title {
  margin-left: auto;
  margin-right: auto;
  height: 56px;
  width: 360px;
  font-family: "PingFang SC";
  font-size: 40px;
  font-weight: 600;
  line-height: 56px;
  text-align: left;
}

.label {
  margin-left: auto;
  margin-right: auto;
  width: 360px;
  height: 30px;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  line-height: 22px;
  text-align: left;
}

input {
  padding-left: 10px;
  font-size: 20px;
  width: 350px;
  height: 48px;
  background: #f2f3f5;
  border: 1px solid #eeeeee;
  border-radius: 4px;
}

.inputBox {
  display: block;
  margin-top: 20px;
}

.checkBox {
  line-height: 48px;
  display: flex;
  width: 360px;
  height: 48px;
  margin-left: auto;
  margin-right: auto;
}

.remAcc {
  display: flex;
  width: 180px;
  height: 48px;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
}

.forgetAcc {
  width: 180px;
  height: 48px;
  text-align: right;
  color: #165dff;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
}

.login {
  position: relative;
  width: 20px;
  height: 20px;
  margin: 12px 10px 0px 0px;
  border: 1px solid #eeeeee;
  border-radius: 2px;
  background-color: #f2f3f5;
}

.tick::after {
  content: " ";
  position: absolute;
  display: inline-block;
  width: 12px;
  height: 6px;
  border-width: 0 0 2px 2px;
  overflow: hidden;
  border-color: #000;
  border-style: solid;
  -webkit-transform: rotate(-50deg);
  transform: rotate(-50deg);
  left: 3px;
  top: 4px;
}

.btn {
  cursor: pointer;
  margin-top: 10px;
  background-color: #165dff;
  width: 360px;
  height: 48px;
  color: #fff;
  border: 1px solid #eeeeee;
  box-shadow: 0px 8px 32px -8px rgba(22, 93, 255, 0.4);
  border-radius: 4px;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 600;
  font-size: 16px;
  line-height: 22px;
}

.register {
  text-decoration: none;
  margin-top: 15px;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  line-height: 22px;
  color: #999999;
  text-align: center;
}

.check {
  display: inline-block;
  color: red;
}

.hide {
  display: none;
}
</style>
