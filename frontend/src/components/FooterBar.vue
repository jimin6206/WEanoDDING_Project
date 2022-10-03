<template>
<footer id="footer">
  <div id="letter" style="margin-top:1rem">
        <div id="parent">
          <div class="child">
            <ul class="list">
              <li class="main-letter" style="font-size:36px;">WEanoDDING</li>
              <li class="f_content footer-font" style="margin-top:0.5rem;">비대면 결혼식 서비스</li>

            </ul>
          </div>
          <div class="child">
            <ul class="list">
              <li class="f_content main-letter" style="font-size:25px; float:left">MENU</li>
              <div style="float:left; width:23rem; margin-top:0.5rem " >
                <a href="/introduce">ABOUT US&nbsp;&nbsp;&nbsp;</a>
                <a href="/management">MANAGEMENT&nbsp;&nbsp;&nbsp;</a>
                <a href="/questions">QUESTIONS&nbsp;&nbsp;&nbsp;</a>
                <a href="/notice">NOTICE&nbsp;&nbsp;&nbsp;</a>
              </div>

            </ul>
          </div>
          <div class="child ">
            <ul class="list">
              <li class="f_content main-letter" style="font-size:25px;">Contact</li>
              <li class="f_content" style="margin-left:3rem; margin-top:0.5rem; font-size:17px">ssafy@ssafy.com</li>
            </ul>
          </div>
        </div>
        <hr style="border:3px">
        <div> <h5 >© 2022 WEanoDDING. All Rights Reserved</h5> </div>
    </div>
</footer>
</template>

<script>

export default {
}
</script>

<style scoped>
  @import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@700&display=swap');
  @import url('https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap');

  #footer {
    background: #0D1903;
    display: flex;
    height: 300px;
    color: white
  }
  #main-letter{
    font-family: 'Quicksand', sans-serif;
  }
  #letter{
    font-family: 'Quicksand', sans-serif;
    width: 90%;
    margin: auto;
    margin-top: 30px;
    font-size: 25px;
  }
  #parent{
    float: center;
    display: flex;
    margin-top: 45px;
  }
  .footer-font{
    font-family: 'Gowun Dodum', sans-serif;
  }

  .list{
    padding-left: 0rem;
    font-family: 'Quicksand', sans-serif;
    list-style: none;
    font-size: 15px;
    width: 15rem;
  }
  .child{
    flex: 1;
    width: 30%;
  }
  .f_content{
    margin-top: 15px;
  }
  a{
    text-decoration: none;
    color: white
  }
  a:hover{
    font-weight: bold;
    color: #FF73A5;
  }

</style>
