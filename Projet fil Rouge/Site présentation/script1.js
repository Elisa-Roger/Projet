// Réalisation du carrousel page Chasselas 

//Récupere mes fleches

let flecheDroite = document.getElementById('fleched');
let flecheGauche = document.getElementById('flecheg');
let image = document.getElementById('ch');

//tableau d'image
let imagesChasselas = ["images/Chasselas/img1.jpg","images/Chasselas/img2.jpg","images/Chasselas/img3.jpg","images/Chasselas/img4.jpg"];
console.log(imagesChasselas);

//changement d'image

 let index=0;

flecheDroite.addEventListener('click',() => {
     index = (index+1) % imagesChasselas.length;
     console.log(index);
     image.src = imagesChasselas[index];
 },false);


 flecheGauche.addEventListener('click',() => {
   index = (index-1) % imagesChasselas.length;
   if (index < 0) {
     index = index + imagesChasselas.length;
   }
   image.src = imagesChasselas[index];
 },false);
