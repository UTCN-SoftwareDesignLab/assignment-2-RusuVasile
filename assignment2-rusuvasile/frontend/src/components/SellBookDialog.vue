<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="brown darken-1" dark floating> "Sell book" </v-toolbar>
        <v-form>
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.quantity" label="Quantity" />
          <v-text-field v-model="book.price" label="Price" />
        </v-form>
        <v-card-actions>
          <v-btn @click="sellBook">Sell Book</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "SellBookDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    sellBook() {
      api.books
        .sell({
          id: this.book.id,
        })
        .then(() => this.$emit("refresh"));
      if (this.book.quantity == 0) alert("OUT OF STOCK");
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
