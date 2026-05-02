document.addEventListener("DOMContentLoaded", function () {
  const templateSelect = document.getElementById("templateSelect");
  const messageInput = document.getElementById("message");

  if (!templateSelect || !messageInput) {
    return;
  }

  templateSelect.addEventListener("change", function () {
    const templateId = templateSelect.value;

    if (!templateId) {
      return;
    }

    fetch("/api/mail/template?templateId=" + encodeURIComponent(templateId))
      .then(function (response) {
        if (!response.ok) {
          throw new Error("Failed to load template.");
        }
        return response.json();
      })
      .then(function (template) {
        messageInput.value = template.templateText;
      })
      .catch(function () {
        alert("Failed to load template.");
      });
  });
});
