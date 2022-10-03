<template>
  <div class="aboutus">

    <div class="AboutUsLine">
      <div class="Aboutus_left">
        <img class="AboutUs_img1" src="@/assets/AboutUs_img2.jpg">
      </div>
      <div class="Aboutus_right">
        <p class="AboutUsTitle">About Us</p>
        <p class="AboutUsContent">청첩장을 받았는데 결혼식에 참석할지 말지 고민한 적 있으신가요?<br/>
           결혼식에 꼭 가고싶은데 가지못하는 상황은 없으셨나요?<br/>
           혹은 본인 결혼식에 누군가 꼭 참석하길 바란 적은 없으셨나요?<br/>
           저희 웨안오딩 서비스는 이 모든 걸 해결해드립니다.</p>
      </div>
    </div>

    <div class="line" text-align="center"></div>
    <p class="ServiceTitle">Our Services</p>

    <div class="AboutUsLine">
      <div class="Aboutus_left">
        <div class="ServiceBox">
          <div class="number">1</div>
          <div class="text">
            <p class="text_title">방명록 작성</p>
            <p>결혼식에 가면 하객들이 방명록을 작성하죠? <br>
            저희 서비스는 비대면 하객들은 위한 방명록을 준비했습니다. <br>
            비대면 하객들은 접속과 동시에 방명록을 손쉽게 작성할 수 있으며 <br>
            식이 끝난 후, 신랑 신부에게 방명록이 전달됩니다.</p>
          </div>
        </div>
        <div class="ServiceBox">
          <div class="number">2</div>
          <div class="text">
            <p class="text_title">축의금 전달</p>
            <p>비대면 하객들을 위한 축의금 서비스도 가능합니다.<br>
            버튼 한번으로 간편하게 신랑,신부 측에게 축의금이 전달됩니다.<br>
            쉽고 빠르게 축의금을 전달하세요.
            </p>
          </div>
        </div>
        <div class="ServiceBox">
          <div class="number">3</div>
          <div class="text">
            <p class="text_title">신랑, 신부 측과의 인사</p>
            <p>신랑 신부대기실에 비대면으로 입장할 수 있습니다.<br>
            신랑 신부과 얼굴을 보며 축하의 인사를 전하세요.<br></p>
          </div>
        </div>
      </div>
      <div class="Aboutus_right">
        <img class="AboutUs_img1" src="@/assets/AboutUs_img1.jpg">
      </div>
    </div>


    <div class="AboutUsLine">

      <div class="Aboutus_left">
        <img id="AboutUs_img3" src="@/assets/AboutUs_img3.jpg">
      </div>
      <div class="Aboutus_right">
        <div class="ServiceBox">
          <div class="number">4</div>
          <div class="text">
            <p class="text_title">결혼식 진행</p>
            <p>결혼식 시작부터 끝까지 실시간으로 볼 수 있습니다.<br>
            현장과 똑같은 화면을 보며 신랑, 신부에게 박수를 쳐주세요.
            </p>
          </div>
        </div>
        <div class="ServiceBox">
          <div class="number">5</div>
          <div class="text">
            <p class="text_title">식사 및 답례품 증정</p>
            <p>결혼식이 끝난 후, 뷔페를 먹거나 답례품을 받아서 집으로갑니다.<br>
            비대면하객들은 못한다구요?<br>
            비대면 하객들에게도 작은 기프티콘을 선물로 받을 수 있습니다.
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'introduce-view',
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=DM+Serif+Display&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Chonburi&display=swap');

.aboutus{
  height: 2200px;
  background: #f0ebe7;
}

.AboutUsLine{
  width: 100%;
  height: 600px;
}

.Aboutus_left{
  float: left;
  width: 50%;
  height: 100%;
  padding-left: 100px;
  padding-top: 100px;
  /* border: 1px solid #0D1903; */
}
.Aboutus_right{
  float: left;
  width: 50%;
  height: 100%;
  padding-right: 100px;
  padding-top: 100px;
  /* border: 1px solid #0D1903; */
}

.AboutUs_img1{
  width: 400px;
  height: 500px;
}

#AboutUs_img3{
  width: 500px;
  height: 350px;
}

.AboutUsTitle{
  margin-top:100px;
  font-size: 50px;
  color: #0D1903;
  font-family: 'DM Serif Display', serif;
  text-align: left;
}
.AboutUsContent{
  font-family: 'Gowun Dodum', sans-serif;
  color: #0D1903;
  text-align: left;
}
.ServiceTitle{
  margin-top:100px;
  font-size: 50px;
  color: #0D1903;
  font-family: 'DM Serif Display', serif;
}

.ServiceBox{
  width: 100%;
  height: 200px;
}

.number{
  float: left;
  height: 200px;
  width: 100px;
  font-family: 'Chonburi', cursive;
  font-size: 100px;
  margin-left: 100px;
  color: #d3c4b8;
}

.text{
  font-family: 'Gowun Dodum', sans-serif;
  color: #0D1903;
  text-align: left;
  margin-left: 250px;
}

.text_title{
  font-size: 20px;
  font-weight: 900;
}

.line{
  margin-top:100px;
  left:0; right:0; margin-left:auto; margin-right:auto;
  width: 70%;
  border-bottom: 1px solid #0D1903;
}

</style>
