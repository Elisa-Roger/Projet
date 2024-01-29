//Fonctionnalité 1
//Création du dark mode
const boutton1 = document.getElementById("soleil");
boutton1.addEventListener("click", () => {
  document.body.classList.remove("dark-mode");
});

const boutton2 = document.getElementById("lune");
boutton2.addEventListener("click", () => {
  document.body.classList.add("dark-mode");
});

//Fonctionnalité 2
// Evenement au survol sur les différents block dans la page Sponsors

let block1 = document.getElementsByClassName("block");
for (let i = 0; i < block1.length; i++) {
  block1[i].addEventListener("mouseenter", () => {
    block1[i].style.background = "linear-gradient(#2d2d2d,#70636300)";
  });
}
let block2 = document.getElementsByClassName("block");
for (let i = 0; i < block2.length; i++) {
  block2[i].addEventListener("mouseleave", () => {
    block2[i].style.background = "linear-gradient(#D90404,#bf111f00)";
  });
}

let texteb = document.getElementsByClassName("textb");
for (let i = 0; i < texteb.length; i++) {
  texteb[i].addEventListener("mouseenter", () => {
    texteb[i].style.color = "#f2f2f2";
  });
}
let texteb2 = document.getElementsByClassName("textb");
for (let i = 0; i < texteb2.length; i++) {
  texteb2[i].addEventListener("mouseleave", () => {
    texteb2[i].style.color = "#0d0d0d";
  });
}
//Fonctionnalité 4
// Menu burger
let icons = document.getElementById('icons');
icons.addEventListener("click", () => {
  nav.classList.toggle("active");
});


