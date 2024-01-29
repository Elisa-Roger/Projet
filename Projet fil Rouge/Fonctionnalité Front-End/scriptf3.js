//Fonctionnalité 3
// Réalisation du carrousel
//tableau d'image
let imagesCdt = [
    "images/Côtes du Tarn/img1.jpg",
    "images/Côtes du Tarn/img2.jpg",
    "images/Côtes du Tarn/img3.jpg",
    "images/Côtes du Tarn/img4.jpg",
    "images/Côtes du Tarn/img5.jpg",
  ];
  
  console.log(imagesCdt);
  
  let image2 = document.getElementById("cdt");
  //Récupere mes fleches
  let flecheDroite = document.getElementById("fleched");
  let flecheGauche = document.getElementById("flecheg");
  
  //changement d'image
  let index = 0;
  
  flecheDroite.addEventListener("click",() => {
    index = (index + 1) % imagesCdt.length;
    console.log(index);
    image2.src = imagesCdt[index];
  },false);
  
  flecheGauche.addEventListener("click",() => {
    index = (index - 1) % imagesCdt.length;
    if (index < 0) {
      index = index + imagesCdt.length;
    }
    image2.src = imagesCdt[index];
  },false);