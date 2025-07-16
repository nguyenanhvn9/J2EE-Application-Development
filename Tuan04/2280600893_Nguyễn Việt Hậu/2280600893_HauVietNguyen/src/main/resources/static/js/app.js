// ✅ Bootstrap validation for forms
(() => {
    'use strict';
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
})();

// ✅ Di chuyển item giữa 2 danh sách trong trang đăng ký lớp học
function moveSelected(fromId, toId) {
    const from = document.getElementById(fromId);
    const to = document.getElementById(toId);
    Array.from(from.selectedOptions).forEach(option => {
        option.selected = true; // đảm bảo submit được
        to.appendChild(option);
    });
}

// ✅ Trước khi submit form đăng ký lớp học, chọn tất cả item ở danh sách phải
document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");
    const registered = document.getElementById("registered");

    if (form && registered) {
        form.addEventListener("submit", function () {
            Array.from(registered.options).forEach(option => option.selected = true);
        });
    }

        document.querySelectorAll('.toggle-btn').forEach(icon => {
            icon.addEventListener('click', () => {
                const li = icon.closest('.collapsible');
                li.classList.toggle('collapsed');

                icon.classList.toggle('bi-caret-right-fill');
                icon.classList.toggle('bi-caret-down-fill');
            });
        });
    });