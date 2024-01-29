// Réalisation du carrousel page Cote du Tarn
//Récupere mes fleches

let flecheDroite = document.getElementById('fleched');
let flecheGauche = document.getElementById('flecheg');

//tableau d'image
let imagesCdt = ["images/Côtes du Tarn/img1.jpg","images/Côtes du Tarn/img2.jpg","images/Côtes du Tarn/img3.jpg","images/Côtes du Tarn/img4.jpg","images/Côtes du Tarn/img5.jpg"];
console.log(imagesCdt);
let image2 = document.getElementById('cdt');
//changement d'image

 let ind=0;

 flecheDroite.addEventListener('click',() => {
     ind = (ind+1) % imagesCdt.length;
     console.log(ind);
     image2.src = imagesCdt[ind];
 },false);


 flecheGauche.addEventListener('click',() => {
   ind = (ind-1) % imagesCdt.length;
   if (ind < 0) {
     ind = ind + imagesCdt.length;
   }
   image2.src = imagesCdt[ind];
 },false);
