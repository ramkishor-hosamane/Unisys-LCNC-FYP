class MessageBox {
    constructor(id, option) {
      this.id = id;
      this.option = option;
    }
    
    show(msg, label = "CLOSE", callback = null) {
      if (this.id === null || typeof this.id === "undefined") {
        // if the ID is not set or if the ID is undefined
        
        throw "Please set the 'ID' of the message box container.";
      }
      
      if (msg === "" || typeof msg === "undefined" || msg === null) {
        // If the 'msg' parameter is not set, throw an error
        
        throw "The 'msg' parameter is empty.";
      }
      
      if (typeof label === "undefined" || label === null) {
        // Of the label is undefined, or if it is null
        
        label = "CLOSE";
      }
      
      let option = this.option;
  
      let msgboxArea = document.querySelector(this.id);
      let msgboxBox = document.createElement("DIV");
      let msgboxContent = document.createElement("DIV");
      let msgboxClose = document.createElement("A");
      
      if (msgboxArea === null) {
        // If there is no Message Box container found.
        
        throw "The Message Box container is not found.";
      }
  
      // Content area of the message box
      msgboxContent.classList.add("msgbox-content");
      msgboxContent.innerText = msg;
      
      // Close burtton of the message box
      msgboxClose.classList.add("msgbox-close");
      msgboxClose.setAttribute("href", "#");
      msgboxClose.innerText = label;
      
      // Container of the Message Box element
      msgboxBox.classList.add("msgbox-box");
      msgboxBox.appendChild(msgboxContent);
  
      if (option.hideCloseButton === false
          || typeof option.hideCloseButton === "undefined") {
        // If the hideCloseButton flag is false, or if it is undefined
        
        // Append the close button to the container
        msgboxBox.appendChild(msgboxClose);
      }
  
      msgboxArea.appendChild(msgboxBox);
  
      msgboxClose.addEventListener("click", (evt) => {
        evt.preventDefault();
        
        if (msgboxBox.classList.contains("msgbox-box-hide")) {
          // If the message box already have 'msgbox-box-hide' class
          // This is to avoid the appearance of exception if the close
          // button is clicked multiple times or clicked while hiding.
          
          return;
        }
  
        this.hide(msgboxBox, callback);
      });
  
      if (option.closeTime > 0) {
        this.msgboxTimeout = setTimeout(() => {
          this.hide(msgboxBox, callback);
        }, option.closeTime);
      }
    }
    
    hide(msgboxBox, callback) {
      if (msgboxBox !== null) {
        // If the Message Box is not yet closed
  
        msgboxBox.classList.add("msgbox-box-hide");
      }
      
      msgboxBox.addEventListener("transitionend", () => {
        if (msgboxBox !== null) {
          // If the Message Box is not yet closed
  
          msgboxBox.parentNode.removeChild(msgboxBox);
  
          clearTimeout(this.msgboxTimeout);
          
          if (callback !== null) {
            // If the callback parameter is not null
            callback();
          }
        }
      });
    }
  }
  
  let msgboxShowMessage = document.querySelector("#msgboxShowMessage");
  let msgboxHiddenClose = document.querySelector("#msgboxHiddenClose");
  
  // Creation of Message Box class, and the sample usage
  let msgboxbox = new MessageBox("#msgbox-area", {
    closeTime: 10000,
    hideCloseButton: false
  });
  let msgboxboxPersistent = new MessageBox("#msgbox-area", {
    closeTime: 0
  });
  let msgboxNoClose = new MessageBox("#msgbox-area", {
    closeTime: 2000,
    hideCloseButton: true
  });
  
  function showMessage(msg,color){
    //msgboxboxPersistent.show("Hello! I am a persistent message box! I will hide myself if you close me.");

    //msgboxBox.style.backgroundColor="red"
    msgboxNoClose.show(msg);
    $('#msgbox-area>div').css('background-color',color);

  }
  
//   msgboxShowMessage.addEventListener("click", function() {
//     msgboxbox.show("Hello! I am a non-persistent message box! I will hide myself automatically after 5 seconds, but you may also close me.", null);
//   });
  
//   msgboxHiddenClose.addEventListener("click", function() {
 //  msgboxNoClose.show("Hello! My close button is hidden, but I will close myself after 5 seconds.");
//   });
  
  // Show the message at the beginning
//   msgboxboxPersistent.show(
//     "Hello! I am a message box! I will appear on the page load period. I also have a callback. You may check on 'Console' to see.",
//     "CALLBACK", () => {
//     console.log("I am the callback! Of course, you may add various javascript codes to make the callback function colourful.");
//   });