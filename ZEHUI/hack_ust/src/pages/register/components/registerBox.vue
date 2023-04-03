<template>
  <div class="formPage">
    <div class="title">Register</div>
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
      <div class="inputBox">
        <div class="label">
          Confirm Password：
          <div class="check" :class="emptyConfirmedPassword ? '' : 'hide'">
            Confirm password cannot be empty
          </div>
        </div>
        <input v-model="confirmedPassword" type="password" />
      </div>
      <div>
        <div
          class="check"
          style="margin-top: 10px"
          :class="sameConfirmedPassword ? 'hide' : ''"
        >
          Confirm password is different.
        </div>
        <div
          class="check"
          style="margin-top: 10px"
          :class="accountExist ? '' : 'hide'"
        >
          Username has been used.
        </div>
      </div>
      <button class="btn" type="submit" @click.prevent="register">
        Sign up
      </button>
    </form>
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
      confirmedPassword: "", // confirmed password
      emptyUsername: false, //judge whether username is empty
      emptyPassword: false, //judge whether password is empty
      emptyConfirmedPassword: false, //judge whether confirmedPassword is empty
      sameConfirmedPassword: true, //judge whether password and confirmedPassword are the same
      accountExist: false, //judge whether username has been registered
    };
  },
  methods: {
    // register functiom
    register() {
      //reset parameters
      this.emptyUsername = false;
      this.emptyPassword = false;
      this.emptyConfirmedPassword = false;
      this.sameConfirmedPassword = true;
      this.accountExist = false;
      //judge whether username is empty
      if (this.username == null || this.username == "")
        this.emptyUsername = true;
      else this.emptyUsername = false;

      //judge whether password is empty
      if (this.password == null || this.password == "")
        this.emptyPassword = true;
      else this.emptyPassword = false;

      //judge whether confirmedPassword is empty
      if (this.confirmedPassword == null || this.confirmedPassword == "")
        this.emptyConfirmedPassword = true;
      else this.emptyConfirmedPassword = false;

      //judge whether password and confirmedPassword are the same
      if (this.password != this.confirmedPassword) {
        this.sameConfirmedPassword = false;
      } else {
        this.sameConfirmedPassword = true;
      }

      if (
        !this.emptyUsername &&
        !this.emptyPassword &&
        !this.emptyConfirmedPassword &&
        this.sameConfirmedPassword
      ) {
        axios
          .post("http://localhost:8080/register/action", {
            username: this.username, //data
            password: this.password,
          })
          .then((res) => {
            if (res.data["data"] === 0) {
              this.$router.push("/");
            } else {
              this.accountExist = true; //username has been registered
            }
          });
      }
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
  /* background-color: #165DFF; */
  margin-left: auto;
  margin-right: auto;
  width: 500px;
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

.btn {
  margin-top: 20px;
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
