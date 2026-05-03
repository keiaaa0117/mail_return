function openDeleteModal(button) {
  const id = button.getAttribute("data-id");
  const subject = button.getAttribute("data-subject");

  document.getElementById("modalText").innerText =
    "「" + subject + "」を削除しますか？";

  const form = document.getElementById("deleteForm");
  form.action = "/mail/delete/" + id;

  document.getElementById("deleteModal").classList.add("is-open");
}

function closeModal() {
  document.getElementById("deleteModal").classList.remove("is-open");
}
