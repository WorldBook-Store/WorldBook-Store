/**
 * 
 */
function check_txtUser1() {
	let maTen = $('#form1User').val();
	let regexTen = /^.+$/;

	if (!(regexTen.test(maTen))) {

		$("#lbltbUsermame").html(
			"<i class='text-warning'>* Hãy nhập dữ liệu </i>");

		return false;
	}
	$("#lbltbUsermame")
		.html(
			"<i class='fa fa-check ' style='color: aqua;' aria-hidden='true' ></i>");
	return true;
};
// 
function check_txtPass1() {
	let maPass = $("#form1Pass").val();
	let regex = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z0-9@$!%*#?&]{6,}$/;
	if (!regex.test(maPass)) {
		if (maPass === "") {
			$("#lbltbPass").html(
				"<i class='text-warning'>* Hãy nhập dữ liệu  </i>");
		} else {
			$("#lbltbPass")
				.html(
					"* Mật khẩu phải trên 6 ký tự, phải có môt <br>số một chữ và một ký từ đặc biệt.");
		}
		return false;
	}
	$("#lbltbPass")
		.html(
			"<i class='fa fa-check ' style='color: aqua;' aria-hidden='true' ></i>");
	return true;
};

function check_data_TK1() {
	if (!check_txtUser()) {
		return false;
	}
	if (!check_txtPass()) {
		return false;
	}
	for (let i = 0; i < 1000; i++) {
		continue;
	}

	return true;
}



/**
 *  Đăng ký
 */
function check_txtUser2() {

	let maTen = $('#form2User').val();
	let regexTen = /^.+$/;

	if (!(regexTen.test(maTen))) {

		$("#lbltbError").html(
			"<i class='text-warning'>(*) Hãy nhập dữ liệu cho User name </i>");

		return false;
	}
	return true;
};

// 
function check_txtName2() {
	let maHo = $("#form2Name").val();
	let regex = /^[A-Z][A-Za-z\s]+$/;
	if (!regex.test(maHo)) {
		if (maHo === "") {
			$("#lbltbError").html("<i class='text-danger'>(*) Hãy nhập dữ liệu cho Full name </i>");
		}
		else {
			$("#lbltbError").html("<i class='text-warning'>(*) Full name: Họ có chữ hoa ở đầu và không có số.</i>");
		}
		return false;
	}
	return true;
};
// 
function check_txtEmail2() {

	let maEmail = $("#form2Email").val();
    let regex = /^([A-Za-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    if (!regex.test(maEmail)) {
        if (maEmail === "") {
            $("#lbltbError").html("<i class='text-danger'>* Hãy nhập dữ liệu cho Email</i>");

        }
        else {
            $("#lbltbError").html("<i class='text-warning'>* Email gồm tên va @gmail.com hoặc @email.com.</i>");
        }
        return false;
    }
    return true;
};
// 
function check_txtPass2() {
	let maPass = $("#myPassRegister").val();
	let regex = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&])[A-Za-z0-9@$!%*#?&]{6,}$/;
	if (!regex.test(maPass)) {
		if (maPass === "") {
			$("#lbltbError").html(
				"<i class='text-warning'>(*) Hãy nhập dữ liệu cho Password </i>");
		} else {
			$("#lbltbError")
				.html(
					"(*) Password: Mật khẩu phải trên 6 ký tự, phải có môt <br>số một chữ và một ký từ đặc biệt.");
		}
		return false;
	}
	return true;
};



function check_data_TK2() {
	if (!check_txtUser2()) {
		return false;
	}
	if (!check_txtName2()) {
		return false;
	}
	if (!check_txtEmail2()) {
		return false;
	}
	if (!check_txtPass2()) {
		return false;
	}
	$("#lbltbError")
		.html(
			"<i class='fa fa-check ' style='color: aqua;' aria-hidden='true' ></i>");

	return true;
}


