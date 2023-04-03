<template>
  <div class="adminPage">
    <topBar class="topBar" :username="username"></topBar>
    <div class="left">
      <resultBox :results="options" :sum="sum" :max="max"></resultBox>
    </div>
    <div class="right">
      <voterList></voterList>
    </div>
  </div>
</template>

<script>
import topBar from "../../components/topBar.vue";
import resultBox from "./components/resultBox.vue";
import voterList from "./components/voterList.vue";
import axios from "axios";

export default {
  components: {
    topBar,
    resultBox,
    voterList,
  },
  data() {
    return {
      username: "JAYANG",
      sum: Number, //sum voters
      options: [], //result data
      max: 0, //max vote
    };
  },
  methods: {
    //get all result data
    getDocs() {
      axios.get("http://localhost:8080/client/getAllDoc").then((res) => {
        this.options = res.data["data"];
        //calculate the sum and max
        let sum = 0;
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
.adminPage {
  position: absolute;
  top: 0px;
  left: 0px;
  display: flex;
  width: 100%;
  height: 100%;
}
.topBar {
  position: absolute;
  top: 0px;
  left: 0px;
  margin: 0px;
}
.left {
  width: 60%;
}
.right {
  width: 40%;
}
</style>
