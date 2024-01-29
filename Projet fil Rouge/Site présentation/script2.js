// Réalisation du carrousel page Coeur des vignobles
//Récupere mes fleches

let flecheDroite = document.getElementById('fleched');
let flecheGauche = document.getElementById('flecheg');
//tableau d'image
let imagesCdv = ["images/Coeur des vignobles/img1.jpg","images/Coeur des vignobles/img2.jpg","images/Coeur des vignobles/img3.jpg","images/Coeur des vignobles/img4.jpg","images/Coeur des vignobles/img5.jpg","images/Coeur des vignobles/img6.jpg"];
console.log(imagesCdv);
let image1 = document.getElementById('cdv');
//changement d'image

 let i=0;

 flecheDroite.addEventListener('click',() => {
     i = (i+1) % imagesCdv.length;
     console.log(i);
     image1.src = imagesCdv[i];
 },false);


 flecheGauche.addEventListener('click',() => {
   i = (i-1) % imagesCdv.length;
   if (i < 0) {
     i = i + imagesCdv.length;
   }
   image1.src = imagesCdv[i];
 },false);