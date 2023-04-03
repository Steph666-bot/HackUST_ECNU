<template>
  <div class="clientPage">
    <topBar class="topBar" :username="username"></topBar>

    <div>
      <resultBox :results="options" :sum="sum" :max="max"></resultBox>
      <div class="backBox">
        <div class="back" @click="backToVote()">Back to Vote</div>
        <div class="arrow-right"></div>
      </div>
    </div>
  </div>
</template>

<script>
import topBar from "../../components/topBar.vue";
import resultBox from "./components/resultBox.vue";
import axios from "axios";

export default {
  components: {
    topBar,
    resultBox,
  },
  data() {
    return {
      sum: Number, //sum voters
      options: [],
      username: "JAYANG",
      max: 0, //the max votes
    };
  },
  methods: {
    //go back to the vote page
    backToVote() {
      this.$router.push("/client/" + this.username);
    },
    //get all result data
    getDocs() {
      axios.get("http://localhost:8080/client/getAllDoc").then((res) => {
        this.options = res.data["data"];
        let sum = 0;
        //calculate the sum and max
        for (let i in this.options) {
          sum = sum + this.options[i].count;
          if (this.options[i].count > this.max) {
            this.max = this.options[i].count;
          }
        }
        this.sum = sum;
      });
    },
  },
  //get username and options data before page loaded
  mounted() {
    this.username = this.$route.params.username;
    this.getDocs();
  },
};
</script>

<style scoped>
page {
  background-color: #f5f5f5;
}
.clientPage {
  width: 100%;
  height: 100%;
}
.topBar {
  position: absolute;
  top: 0px;
  left: 0px;
  margin: 0px;
}
.question {
  margin-top: 150px;
  margin-left: 30%;
  margin-bottom: 20px;
  font-size: 35px;
  font-weight: bold;
}
.selectBox {
  margin: 0;
  width: 500px;
  /* margin-left: 30%; */
  margin: 0 auto;
  /* left: 30%; */
}
.item {
  margin: 20px 0;
  width: 350px;
  height: 60px;
  background-color: #fff;
  display: flex;
  box-shadow: 6px 2px 16px #dcdcdc;
  border-radius: 4px;
}
.check {
  width: 23px;
  height: 23px;
  border-radius: 50%;
  border: 3px solid #eeeeee;
  margin: 15px 10px 0px 10px;
  background-color: #fff;
}

.checkItem {
  border: 3px solid rgba(22, 93, 255, 0.8);
  box-shadow: 0px 2px 16px rgba(22, 93, 255, 0.2);
  left: 20px;
}
.tick {
  background-color: #165dff;
}
.tick::after {
  content: " ";
  display: inline-block;
  width: 12px;
  height: 6px;
  border-width: 0 0 3px 3px;
  overflow: hidden;
  border-color: #fff;
  border-style: solid;
  -webkit-transform: rotate(-50deg);
  transform: rotate(-50deg);
  left: 5px;
  top: 6px;
  margin-left: 3px;
  margin-bottom: 2px;
}
.name {
  line-height: 60px;
  font-size: 20px;
  font-weight: bold;
  padding-left: 10px;
}
li {
  list-style: none;
}
.submitBtn {
  /* margin-left: 32%; */
  margin-top: 20px;
  width: 192px;
  height: 60px;
  background-color: #165dff;
  border: 1px solid #eeeeee;
  box-shadow: 0px 8px 32px -8px rgba(22, 93, 255, 0.4);
  border-radius: 4px;
  color: #fff;
  font-family: "PingFang SC";
  font-style: normal;
  font-weight: 600;
  font-size: 18px;
  line-height: 60px;
  text-align: center;
}
.alert {
  color: red;
  margin-left: 30%;
}
.backBox {
  margin: 20px auto;
  width: 500px;
  margin-bottom: 100px;
  display: flex;
}
.back {
  width: 180px;
  line-height: 40px;
  font-size: 25px;
  border-bottom: 3px solid #165dff;
  color: #165dff;
  cursor: pointer;
  font-weight: bold;
  z-index: 999;
}
.hide {
  display: none;
}
.arrow-right {
  width: 0;
  height: 0;
  position: relative;
  top: 26px;
  right: 13px;
  border: 15px solid;
  border-color: transparent transparent transparent #165dff;
}
.arrow-right::after {
  content: "";
  position: absolute;
  top: -20px;
  left: -25px;
  border: 20px solid;
  border-color: transparent transparent transparent white;
}
</style>
