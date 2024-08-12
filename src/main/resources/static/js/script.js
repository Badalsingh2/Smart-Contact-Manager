console.log("Script loaded");

let currentTheme=getTheme();
//console.log(currentTheme);


document.addEventListener('DOMContentLoaded',() =>{
    changeTheme(currentTheme);
});



function changeTheme(){
    //set to web page
    changePageeTheme(currentTheme,"")
    //set the listener to change theme button
    const changeThemeButton=document.querySelector('#theme_change_button');
     //change the text of button
    
    changeThemeButton.addEventListener("click", (event) => {
        let oldTheme = currentTheme;
        if(currentTheme === "dark"){
            currentTheme = "light";
        }
        else{
            currentTheme="dark";
        }

       changePageeTheme(currentTheme,oldTheme)
    });
}


//set theme to localstorage
function setTheme(theme){
    localStorage.setItem("theme",theme);
}


//get theme to localstorage
function getTheme(){
    let theme=localStorage.getItem("theme");
     
    return theme ? theme :"light";
}

function changePageeTheme(theme,oldTheme){
    //localstorage updation
    setTheme(currentTheme);

    //remove the current theme
    if(oldTheme){
       //console.log("removing theme");
       document.querySelector('html').classList.remove(oldTheme);

    }

    
    document.querySelector('html').classList.add(theme);

    //change the text of button
    document.querySelector('#theme_change_button').querySelector('span').textContent = theme == "light"?" Dark":" Light";
    
}