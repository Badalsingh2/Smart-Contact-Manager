console.log("Contacting")
const baseUrl = "http://localhost:8081";
const viewContactModal=document.getElementById("view_contact_modal");

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const modal=new Modal(viewContactModal,options,instanceOptions);

function openContactModal(){
    modal.show();
}

function closeContactModal(){
    modal.hide();
}

async function loadContactData(id){
    //function call to load data
    console.log(id);
    try {
        const data=await (await fetch(`${baseUrl}/api/contacts/${id}`)).json();
        console.log(data); 
        document.querySelector("#contact_name").innerHTML=data.name;
        document.querySelector("#contact_email").innerHTML=data.email;
        document.querySelector("#contact_phonenumber").innerHTML=data.phoneNumber;
        document.querySelector("#contact_address").innerHTML=data.address;
        document.querySelector("#contact_description").innerHTML=data.description;
        document.querySelector("#contact_website").innerHTML=data.websiteLink;
        document.querySelector("#contact_linkedin").innerHTML=data.linkedInLink;    
        document.querySelector("#contact_website").href=data.websiteLink;
        document.querySelector("#contact_linkedin").href=data.linkedInLink;            
        document.querySelector("#contact_Image").src=data.pictures;        
        openContactModal();
    } catch (error) {
        console.log("Error: ",error);
    }
    
}

//delete contact
async function deleteContact(id){
    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover this Contact!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      })
      .then((willDelete) => {
        if (willDelete) {
          const url=`${baseUrl}/user/contacts/delete/`+id;
          window.location.replace(url);
        }
      });
}