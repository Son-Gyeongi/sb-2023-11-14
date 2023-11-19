toastr.options = {
    closeButton: true,
    debug: false,
    newestOnTop: true,
    progressBar: true,
    positionClass: "toast-top-right",
    preventDuplicates: false,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "5000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut"
};

function toastWarning(msg) {
    toastr["warning"](msg, "경고");
}

function toastNotice(msg) {
    toastr["success"](msg, "성공");
}

// 자바스크립트에서 현재 페이지 url 뒤에 파라미터를 객체 형태로 얻기
function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    const paramsObj = {};

    for (const [key, value] of params) {
        paramsObj[key] = value;
    }

    return paramsObj;
}