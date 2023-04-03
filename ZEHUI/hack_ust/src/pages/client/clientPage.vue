<template>
  <div class="clientPage">
    <topBar class="topBar" :username="username"></topBar>
    <div>
      <div class="question">Who is the GOAT sports player?</div>
      <div class="selectBox">
        <li v-for="item in options">
          <div
            class="item"
            @click="select(item.id)"
            :class="check == item.id ? 'checkItem' : ' '"
          >
            <div class="check" :class="check == item.id ? 'tick' : ' '"></div>
            <div class="name">{{ item.doc_identifier }}</div>
          </div>
        </li>
        <div class="alert" :class="choose ? 'hide' : ''">
          Please choose one!
        </div>
        <div class="submitBtn" @click="submitOption()">Submit</div>
      </div>
    </div>
  </div>
</template>

<script>
import topBar from "../../components/topBar.vue";
import axios from "axios";

export default {
  components: {
    topBar,
  },
  data() {
    return {
      check: -1, //checked option index
      choose: true, //judge whether client has chosen an option
      options: [
        //options
        { id: 1, doc_identifier: "Michael Jordan" },
        { id: 2, doc_identifier: "Lin Dan" },
        { id: 3, doc_identifier: "Lionel Messi" },
        { id: 4, doc_identifier: "Usain Bolt" },
        { id: 5, doc_identifier: "Michael Phelps" },
      ],
      username: "JAYANG",
    };
  },
  methods: {
    select(id) {
      if (this.check === id) {
        this.check = -1;
      } else {
        this.check = id;
      }
    },
    //submit function
    submitOption() {
      if (this.check === -1) {
        this.choose = false; //user must choose a option
      } else {
        let date = new Date().getTime(); //date as nonce
        axios
          .post("http://localhost:8080/client/submit", {
            pk: this.username,
            nonce: date,
            doc_identifier: this.options[this.check - 1].id,
          })
          .then((res) => {
            this.$router.push("/client/resultPage" + this.username);
          });
      }
    },
    //get all options data
    getDocs() {
      axios.get("http://localhost:8080/client/getAllDoc").then((res) => {
        this.options = res.data["data"];
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
.hide {
  display: none;
}
</style>
