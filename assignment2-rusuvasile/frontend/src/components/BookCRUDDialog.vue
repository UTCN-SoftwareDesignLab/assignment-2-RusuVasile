<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="brown darken-1" dark floating>
          {{ isNew ? "Create book" : "Edit book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.quantity" label="Quantity" />
          <v-text-field v-model="book.price" label="Price" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">{{ isNew ? "Create" : "Save" }}</v-btn>
          <v-btn @click="deleteBook">Delete Book</v-btn>
          <v-btn @click="sellBook">Sell Book</v-btn>
          <v-btn @click="csvReport">Report CSV</v-btn>
          <v-btn @click="pdfReport">Report PDF</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookCRUDDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.books
          .create({
            author: this.book.author,
            title: this.book.title,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.books
          .edit({
            id: this.book.id,
            author: this.book.author,
            title: this.book.title,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      }
    },

    deleteBook() {
      api.books
        .delete({
          id: this.book.id,
        })
        .then(() => this.$emit("refresh"));
    },

    sellBook() {
      api.books
        .sell({
          id: this.book.id,
        })
        .then(() => this.$emit("refresh"));
      if (this.book.quantity == 0) {
        alert("OUT OF STOCK");
      }
    },
    pdfReport() {
      //alert("CSV Report Downloaded");
      api.books.downloadPdf();
    },
    csvReport() {
      api.books.downloadCsv();
    },
  },
  computed: {
    isNew: function () {
      return !this.book.id;
    },
  },
};
</script>

<style scoped></style>
