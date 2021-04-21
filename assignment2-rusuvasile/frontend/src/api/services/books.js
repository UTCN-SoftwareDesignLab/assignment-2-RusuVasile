import authHeader, { BASE_URL, HTTP } from "../http";
import axios from "axios";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/fo", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/fo", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  edit(book) {
    return HTTP.patch(BASE_URL + "/fo", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  delete(book) {
    return HTTP.delete(BASE_URL + "/fo/" + book.id, {
      headers: authHeader(),
    }).then();
  },

  sell(book) {
    return HTTP.patch(BASE_URL + "/fo/sell", book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  downloadPdf() {
    return HTTP.get(BASE_URL + "/fo/downloadPdf", {
      headers: authHeader(),
    }).then();
  },
  downloadCsv() {
    return HTTP.get(BASE_URL + "/fo/downloadCsv", {
      headers: authHeader(),
    }).then();
  },

  getPdf() {
    axios({
      headers: authHeader(),
      url: BASE_URL + "/fo/getPdf-file",
      method: "GET",
      responseType: "blob",
    }).then((response) => {
      var fileURL = window.URL.createObjectURL(new Blob([response.data]));
      var fileLink = document.createElement("a");

      fileLink.href = fileURL;
      fileLink.setAttribute("download", "pdfFile.pdf");
      document.body.appendChild(fileLink);

      fileLink.click();
    });
  },
};
