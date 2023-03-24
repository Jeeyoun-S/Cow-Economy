<template>
  <div id="stopwatch">
    <div class="switch"></div>
    <div class="pie pie1"></div>
    <div class="pie pie2"></div>
    <div class="ring"></div>
  </div>
</template>

<style lang="scss">
$frameColor: slategrey;
$buttonColor: slategrey;
$ringColor: black;
$lapTime: 10s;
$infiniteLoop: true;
$size: 3em;

#stopwatch {
  * {
    box-sizing: border-box;
  }
  position: relative;
  display: inline-block;
  //   margin: 0.5em;
  // margin-left: 6%;
  // margin-bottom: 2%;
  width: $size;
  height: $size;

  .switch {
    position: absolute;
    left: (0.38 * $size);
    width: (0.24 * $size);
    height: (0.1 * $size);
    background-color: $buttonColor;
    animation: pressure $lapTime ease-out;
    @if $infiniteLoop == true {
      animation-iteration-count: infinite;
    }
    box-shadow: 0 0 (0.02 * $size) 0 black;
  }

  .ring {
    position: absolute;
    top: (0.12 * $size);
    left: (0.1 * $size);
    border: (0.06 * $size) solid $ringColor;
    width: (0.8 * $size);
    height: (0.8 * $size);
    border-radius: 50%;
    z-index: 500;
    box-shadow: inset 0 0 (0.03 * $size) 0 black;
  }

  .pie {
    position: absolute;
    left: (0.05 * $size);
    top: (0.07 * $size);
    width: (0.9 * $size);
    height: (0.9 * $size);
    border-radius: 50%;
    border: (0.1 * $size) solid $frameColor;
    box-shadow: 0 0 (0.03 * $size) 0 black;
  }

  .pie::before {
    content: "";
    display: block;
    margin-left: 50%;
    height: 100%;
    border-radius: 0 100% 100% 0 / 50%;
    transform-origin: left;
  }

  .pie1 {
    z-index: 10;
    transform: rotate(1turn);
    background-image: linear-gradient(to left, transprent 50%, blue 0);
  }

  .pie1::before {
    background-color: black;
    transform: rotate(1turn);
    animation: spin1 ($lapTime/2) linear;
    @if $infiniteLoop == true {
      animation-iteration-count: infinite;
    }
  }

  .pie2 {
    z-index: 0;
    transform: rotate(1turn);
    background-image: linear-gradient(to left, transparent 50%, black 0);
    animation: altitude $lapTime step-end, temperature $lapTime linear;
    @if $infiniteLoop == true {
      animation-iteration-count: infinite;
    }
  }

  .pie2::before {
    transform: rotate(1turn);
    animation: spin2 ($lapTime/2) linear infinite, temperature $lapTime linear;
    @if $infiniteLoop == true {
      animation-iteration-count: infinite;
    }
  }

  @keyframes spin1 {
    0% {
      transform: rotate(1turn);
    }
    100% {
      transform: rotate(1.5turn);
    }
  }

  @keyframes spin2 {
    0% {
      transform: rotate(1turn);
    }
    100% {
      transform: rotate(1.5turn);
    }
  }

  @keyframes altitude {
    50% {
      z-index: 10;
    }
  }

  @keyframes temperature {
    0% {
      background-color: blue;
    }
    50% {
      background-color: violet;
    }
    100% {
      background-color: red;
    }
  }

  @keyframes pressure {
    0% {
      top: 0px;
    }
    2% {
      top: (0.05 * $size);
    }
    4% {
      top: 0px;
    }
  }
}
</style>
