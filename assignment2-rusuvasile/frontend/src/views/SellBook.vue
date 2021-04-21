<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="sellBook"
    ></v-data-table
    ><SellBookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></SellBookDialog>
  </v-card>
</template>
<script>
import api from "../api";
import SellBookDialog from "../components/SellBookDialog";
export default {
  name: "BookList",
  components: { SellBookDialog },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "author", value: "author" },
        { text: "genre", value: "genre" },
        { text: "price", value: "price" },
        { text: "quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    sellBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>
exp
<style scoped></style>
