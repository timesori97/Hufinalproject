<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<!-- jQuery는 하나만 불러오도록 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<style>
body {
	font-family: sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 40px 0;
	display: flex;
	justify-content: center;
	align-items: flex-start;
}

#signUpForm {
	background-color: #fff;
	padding: 30px 40px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	width: 500px;
}

#signUp td {
	padding: 10px 0;
	vertical-align: top;
}

#signUp label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
}

#signUp input[type="text"], #signUp input[type="password"], #signUp input[type="email"],
	#signUp select {
	width: calc(100% - 20px);
	padding: 8px 10px;
	font-size: 14px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

#signUp input[readonly] {
	background-color: #e9ecef;
	cursor: not-allowed;
}

/* 버튼 2개 같은 줄에 배치 + 가운데 정렬 */
.button-group {
	display: flex;
	gap: 10px;
	margin-top: 5px;
	justify-content: center; /* 가운데 정렬 */
}

#signUp button[type="submit"] {
	padding: 10px 16px;
	background-color: #007bff;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
	transition: background-color 0.3s ease;
	margin-top: 5px;
}

#signUp button[type="submit"]:hover {
	background-color: #0056b3;
}

#cancelBtn {
	padding: 10px 16px;
	background-color: #6c757d; /* 회색 */
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
	transition: background-color 0.3s ease;
	margin-top: 5px;
}

#cancelBtn:hover {
	background-color: #5a6268; /* 어두운 회색 */
}

#signUp button[type="button"] {
	padding: 10px 16px;
	font-size: 14px;
	border-radius: 4px;
	border: none;
	cursor: pointer;
	transition: background-color 0.3s ease;
	margin-top: 5px;
}

/* 중복체크 버튼 스타일 */
#idCheckBtn {
	padding: 8px 14px;
	background-color: #007bff;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
	transition: background-color 0.3s ease;
	margin-top: 0;
	white-space: nowrap;
	min-width: 90px;
}

#idCheckBtn:hover {
	background-color: #0056b3;
}

#signUp span {
	padding: 0 5px;
	font-weight: bold;
}

.hidden-field {
	display: none;
}

/* 이메일 입력 개선 */
.email-group {
	display: flex;
	align-items: center;
	flex-wrap: wrap;
	gap: 5px;
}

.email-group input[type="text"], .email-group select {
	width: auto;
	min-width: 120px;
	flex: 1;
}

.email-group span {
	font-weight: bold;
	padding: 0 5px;
}

/* 아이디 입력 + 중복체크 버튼 컨테이너 */
.id-check-container {
	display: flex;
	align-items: center;
	gap: 10px;
}

#signUp button.zipcode-btn {
	padding: 10px 16px;
	background-color: #007bff;
	color: white;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
	transition: background-color 0.3s ease;
	margin-left: 10px;
}

#signUp button.zipcode-btn:hover {
	background-color: #0056b3;
}

/* 생년월일 input 스타일링 */
input[type="date"] {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 4px;
	font-size: 14px;
	background-color: #fff;
	color: #333;
	cursor: pointer;
}

input[type="date"]:focus {
	outline: none;
	border-color: #007bff;
	box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
}

/* 생년월일 placeholder 커스텀 */
input[type="date"]::-webkit-datetime-edit-text {
	-webkit-appearance: none;
	display: none;
}

input[type="date"]::-webkit-datetime-edit-month-field {
	-webkit-appearance: none;
	display: none;
}

input[type="date"]::-webkit-datetime-edit-day-field {
	-webkit-appearance: none;
	display: none;
}

input[type="date"]::-webkit-datetime-edit-year-field {
	-webkit-appearance: none;
	display: none;
}

input[type="date"]::before {
	color: #999;
	content: attr(data-placeholder);
}

/* 달력 아이콘 커스텀 */
input[type="date"]::-webkit-calendar-picker-indicator {
	background-color: #007bff;
	padding: 5px;
	cursor: pointer;
	border-radius: 3px;
	color: white;
}

/* 프로필 사진 업로드 영역 */
.profile-upload-container {
	position: relative;
	width: 150px;
	height: 150px;
	margin: 10px auto;
	border: 2px dashed #ddd;
	border-radius: 8px;
	background-color: #f9f9f9;
	cursor: pointer;
	transition: all 0.3s ease;
}

.profile-upload-container:hover {
	border-color: #007bff;
	background-color: #f0f8ff;
}

.profile-upload-container.has-image {
	border: 2px solid #007bff;
	background-color: #fff;
}

/* 파일 input 숨기기 */
#profileImage {
	position: absolute;
	width: 100%;
	height: 100%;
	opacity: 0;
	cursor: pointer;
}

/* 업로드 아이콘 및 텍스트 */
.upload-placeholder {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	text-align: center;
	color: #666;
	pointer-events: none;
}

.upload-placeholder i {
	font-size: 24px;
	margin-bottom: 8px;
	display: block;
}

.upload-placeholder span {
	font-size: 12px;
	display: block;
}

/* 프로필 이미지 미리보기 */
.profile-preview {
	width: 100%;
	height: 100%;
	object-fit: cover;
	border-radius: 6px;
}

/* 파일 검증 메시지 */
#photoValidation {
	margin-top: 5px;
	font-size: 12px;
	text-align: center;
}

#photoValidation.error {
	color: #dc3545;
}

#photoValidation.success {
	color: #28a745;
}

/* 파일 정보 표시 */
.file-info {
	margin-top: 8px;
	font-size: 12px;
	color: #666;
	text-align: center;
}

/* 테이블 내 프로필 업로드 섹션 정렬 */
#signUp tr td .profile-section {
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 10px;
}
</style>

<script>
function searchZipcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var fullAddress = data.address;
            var extraAddress = '';

            if (data.addressType === 'R') {
                if (data.bname !== '') {
                    extraAddress += data.bname;
                }
                if (data.buildingName !== '') {
                    extraAddress += (extraAddress !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddress !== '') {
                    fullAddress += ' (' + extraAddress + ')';
                }
            }

            document.getElementById('zipCode').value = data.zonecode;
            document.getElementById('address').value = fullAddress;
            document.getElementById('detailAddress').focus();
            document.getElementById('fullAddress').value = fullAddress + ' ' + document.getElementById('detailAddress').value;
        }
    }).open();
}

window.addEventListener('DOMContentLoaded', () => {
    // 상세 주소 입력 시 fullAddress 갱신
    document.getElementById('detailAddress').addEventListener('input', function() {
        const full = document.getElementById('address').value + ' ' + this.value;
        document.getElementById('fullAddress').value = full.trim();
    });

});


$(document).ready(function(){
    $("#idCheckBtn").click(function(){
      var userId = $("#userId").val().trim();

      if(userId === "") {
        alert("사용할 아이디를 입력하세요");
        return;
      }

      $.ajax({
        type: "POST",
        url: "${pageContext.request.contextPath}/userIdCheck",
        data: { userId: userId },
        success: function(data){
        	  // data.count로 접근
        	  if(data.count === 0) {
        	    $("#idCheckStatus").text("사용 가능한 아이디입니다.").css("color", "green");
        	  } else {
        	    $("#idCheckStatus").text("이미 사용 중인 아이디입니다.").css("color", "red");
        	  }
        	},
        error: function(){
          alert("서버와 통신 중 오류가 발생했습니다.");
        }
      });
    });
  });
 
$(document).ready(function() {
    // 비밀번호 유효성 검사 (AJAX)
    $('#userPassword').keyup(function() {
        let password = $(this).val();

        if (password === '') {
            $('#passwordCheck1').html('<b style="font-size:14px; color:red;">[사용할 비밀번호를 입력하세요]</b>');
            return;
        }

        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/checkPassword',
            data: { password: password },
            success: function(response) {
                if (response.valid) {
                    $('#passwordCheck1').html('<b style="font-size:14px; color:green;">[사용 가능한 비밀번호입니다.]</b>');
                } else {
                    $('#passwordCheck1').html('<b style="font-size:14px; color:red;">[비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다.]</b>');
                }
            },
            error: function() {
                $('#passwordCheck1').html('<b style="font-size:14px; color:red;">[서버 오류가 발생했습니다.]</b>');
            }
        });
    });

    // 비밀번호 확인란 일치 여부 (동기 처리)
    $('#userPasswordConfirm').keyup(function() {
        let confirmPwd = $(this).val();
        let pwd = $('#userPassword').val();

        if (confirmPwd === '') {
            $('#passwordCheck2').html('<b style="font-size:14px; color:blue;">[비밀번호 확인은 필수 정보입니다.]</b>');
        } else if (confirmPwd !== pwd) {
            $('#passwordCheck2').html('<b style="font-size:14px; color:red;">[입력한 비밀번호가 일치하지 않습니다.]</b>');
        } else {
            $('#passwordCheck2').html('<b style="font-size:14px; color:green;">[비밀번호가 일치합니다.]</b>');
        }
    });

    // 폼 제출 시 클라이언트 측 검증
    $('#signUpForm').submit(function(event) {
        let password = $('#userPassword').val();
        let confirmPwd = $('#userPasswordConfirm').val();

        // 비밀번호 유효성 검사
        if (!password.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/)) {
            alert('비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다.');
            event.preventDefault();
            return false;
        }

        // 비밀번호 일치 여부 확인
        if (password !== confirmPwd) {
            alert('비밀번호가 일치하지 않습니다.');
            event.preventDefault();
            return false;
        }
    });
	<!-- 휴대폰 번호 숫자만 들어가게  검사 -->
	$('input[name="userPhoneNum"]').on('input', function() {
	    this.value = this.value.replace(/[^0-9]/g, ''); // 숫자만 허용
	});
    $('#profileImage').change(function() {
        var file = this.files[0];
        if (file) {
            // 미리보기 표시
            var reader = new FileReader();
            reader.onload = function(e) {
                $('#photoPreview').attr('src', e.target.result).show();
            };
            reader.readAsDataURL(file);
            
            // AJAX로 실시간 검증
            var formData = new FormData();
            formData.append('photo', file);
            
            $.ajax({
                url: '${pageContext.request.contextPath}/common/validatePhoto',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    var validationDiv = $('#photoValidation');
                    if (response.valid) {
                        validationDiv.html('<span class="success">' + response.message + '</span>');
                        if (response.warning) {
                            validationDiv.append('<br><span class="warning">' + response.warning + '</span>');
                        }
                    } else {
                        validationDiv.html('<span class="error">' + response.message + '</span>');
                        $('#photoPreview').hide();
                        $('#profileImage').val('');
                    }
                },
                error: function() {
                    $('#photoValidation').html('<span class="error">검증 중 오류가 발생했습니다.</span>');
                }
            });
        } else {
            $('#photoPreview').hide();
            $('#photoValidation').empty();
        }
    });
});



</script>
</head>

<body>
	<form action="${pageContext.request.contextPath}/join" method="post"
		enctype="multipart/form-data" id="signUpForm">
		<table id="signUp">
			<tr>
				<td><label for="userId">아이디</label>
					<div class="id-check-container">
						<input type="text" name="userId" id="userId"
							placeholder="아이디를 입력하세요" required>
						<button type="button" id="idCheckBtn">중복체크</button>
						<div>
							<font id="idCheckStatus" size="2"></font>
						</div>
					</div></td>
			</tr>
			<tr>
				<td><label for="userPassword">비밀번호</label> <input
					type="password" name="userPassword" id="userPassword"
					placeholder="비밀번호를 입력하세요" required>
					<div id="passwordCheck1"></div></td>
			</tr>
			<tr>
				<td><label for="userPasswordConfirm">비밀번호 확인</label> <input
					type="password" name="userPasswordConfirm" id="userPasswordConfirm"
					placeholder="비밀번호를 한 번 더 입력하세요" required>
					<div id="passwordCheck2"></div></td>
			</tr>

			<tr>
				<td><label for="userName">이름</label> <input type="text"
					name="userName" id="userName" placeholder="이름을 입력하세요" required>
				</td>
			</tr>
			<tr>
				<td><label for="zipCode">우편번호</label> <input type="text"
					name="zipCode" id="zipCode" placeholder="우편번호" readonly required>
					<button type="button" class="zipcode-btn" onclick="searchZipcode()">우편번호검색</button>
				</td>
			</tr>
			<tr>
				<td><label for="address">주소</label> <input type="text"
					id="address" placeholder="주소를 입력하세요" readonly required> <input
					type="text" name="detailAddress" id="detailAddress"
					placeholder="상세 주소를 입력하세요" required> <input type="hidden"
					name="address" id="fullAddress"></td>
			</tr>
			<tr>
				<td><label for="userEmail">이메일</label>
					<div class="email-group">
						<input type="text" name="email_id" id="email_id"
							placeholder="이메일 아이디" required> <span>@</span> <input
							type="hidden" name="email_domain" id="email_domain_input"
							value="naver.com" required /> <select name="emailDomain"
							id="email_domain" required>
							<option value="naver.com" selected>naver.com</option>
							<option value="gmail.com">gmail.com</option>
							<option value="daum.net">daum.net</option>
						</select> <input type="hidden" name="fullEmail" id="fullEmail">
					</div></td>
			</tr>

			<tr>
				<td><label for="phoneNum">휴대폰번호</label> <input type="text"
					id="phoneNum" name="userPhoneNum" maxlength="11"
					placeholder="숫자만 입력" required></td>
			</tr>

			<tr>
				<td><label for="birthdate">생년월일</label> <input type="date"
					name="birthdate" id="birthdate" data-placeholder="생년월일을 선택하세요"
					required /></td>
			</tr>


			<tr>
				<td>
					<div class="profile-section">
						<label for="profileImage">프로필 사진 (여권 규격)</label>
						<div class="profile-upload-container" id="uploadContainer">
							<input type="file" id="profileImage" name="profileImageFile"
								accept="image/jpeg,image/jpg" onchange="handleImageUpload(this)">
							<div class="upload-placeholder" id="uploadPlaceholder">
								<i class="fas fa-camera"></i> <span>사진을 선택하세요</span>
							</div>
							<img id="photoPreview" class="profile-preview"
								style="display: none;">
						</div>
						<div id="photoValidation"></div>
						<div class="file-info" id="fileInfo" style="display: none;"></div>
					</div>
				</td>
			</tr>


			<tr>
				<td>
					<div class="button-group">
						<button type="submit">가입</button>
						<button type="reset" id="cancelBtn">취소</button>
					</div>
				</td>
			</tr>
		</table>



	</form>
</body>
<script>
						 document.getElementById('email_domain').addEventListener('change', function() {
							    const selectedValue = this.value;
							    document.getElementById('email_domain_input').value = selectedValue; // 이메일 도메인 필드에 설정
							});
						 document.getElementById('email_id').addEventListener('input', function() {
							    const emailId = this.value;
							    const emailDomain = document.getElementById('email_domain_input').value;
							    document.getElementById('fullEmail').value = emailId + '@' + emailDomain; // 전체 이메일을 업데이트
							}); 
						 
						 
						


						</script>
</html>
