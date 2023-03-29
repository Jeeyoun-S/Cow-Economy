<template>
  <v-dialog v-model="dialog" max-width="300">
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on" icon text :color="color" :small="isSmall"
        ><v-icon> mdi-delete-forever </v-icon></v-btn
      >
    </template>
    <template v-slot:default="dialog">
      <v-card class="pb-3">
        <v-card-title>메모 삭제하기</v-card-title>
        <v-card-text
          >선택한 메모를 삭제하시겠습니까?<br />삭제된 메모를 복구하는 것은
          불가능합니다.</v-card-text
        >
        <v-card-actions class="justify-center">
          <v-btn
            elevation="0"
            color="var(--error-col-1)"
            @click="deleteMemoItem()"
            dark
            >삭제</v-btn
          >
          <v-btn elevation="0" @click="dialog.value = false">닫기</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import { deleteMemo } from "@/api/modules/memo";
import { mapActions, mapState } from "vuex";

export default {
  name: "NewsDetailMemoDeleteBtn",
  data() {
    return {
      dialog: false,
    };
  },
  computed: {
    ...mapState("memoStore", ["newMemo"]),
  },
  props: {
    memoId: Number,
    index: Number,
    color: String,
    isSmall: Boolean,
    indexDetail: Number,
  },
  methods: {
    ...mapActions("memoStore", ["updateNewMemo", "removeSelectionText"]),
    // 메모 삭제하기
    deleteMemoItem() {
      // 메모 삭제 API 요청 보내기
      deleteMemo(this.memoId).then((res) => {
        // 삭제 성공
        if (res) {
          // 경고창 닫기
          this.dialog = false;
          // 만약 현재 수정 중이었던 메모를 삭제했다면
          if (this.newMemo.memoId == this.memoId) {
            // 등록창 인용문 삭제
            this.removeSelectionText();
            // 등록창 메모 내용 삭제
            this.updateNewMemo({
              isModify: false,
              memoId: null,
              memoContnet: null,
              memoPublicScope: false,
              index: null,
            });
          }
          // 메모 리스트에서 삭제하기
          if (this.isSmall)
            this.$emit("deleteMemoItem", this.index, this.indexDetail);
          else this.$emit("deleteMemoItem");
        } else {
          // 삭제 실패
        }
      });
    },
  },
};
</script>

<style></style>
