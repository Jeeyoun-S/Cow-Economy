import The404Error from "@/views/The404Error.vue";

const error = [
  {
    path: "/:pathMatch(.*)*",
    name: "the404Error",
    component: The404Error,
  },
];

export default error;
