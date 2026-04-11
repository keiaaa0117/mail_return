function openDeleteModal(button) {
  const id = button.getAttribute("data-id");
  const subject = button.getAttribute("data-subject");

  document.getElementById("modalText").innerText = 'Delete "' + subject + '"?';

  const form = document.getElementById("deleteForm");
  form.action = "/mail/delete/" + id;

  document.getElementById("deleteModal").style.display = "block";
}

function closeModal() {
  document.getElementById("deleteModal").style.display = "none";
}
