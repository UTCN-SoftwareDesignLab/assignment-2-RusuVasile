import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/fo", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  sell(book) {
    return HTTP.patch(BASE_URL + "/fo/sell", book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
