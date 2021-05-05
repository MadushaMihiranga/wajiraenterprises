/**
 * @author madusha mihiranga
 * @version 1.0
 * @description javascript library for create UI component
 */



function fillCombo2(combo,message,list,attribute,selectedvalue,attribute2,attribute3) {

    combo.innerHTML="";
    if(message!="") {
        var prompt = document.createElement("option");
        prompt.selected = "selected";
        prompt.disabled = "disabled";
        var prompttext = document.createTextNode(message);
        prompt.appendChild(prompttext);
        combo.appendChild(prompt);
    }

    for (index in  list) {
        var option = document.createElement("option");
        option.value=JSON.stringify(list[index]);
        if(attribute2==null && attribute3==null)
            var text = document.createTextNode(list[index][attribute]);
        else{
            var text = document.createTextNode(list[index][attribute][attribute2][attribute3]);
            console.log(text)
        }
        if(list[index][attribute]==selectedvalue) {
            option.selected = "selected";
            // combo.parentNode.parentNode.classList.add("valid");
            // combo.parentNode.parentNode.classList.remove("invalid");
            // combo.parentNode.parentNode.classList.remove("updated");
            combo.style.background = valid;
        }

        option.appendChild(text);
        combo.appendChild(option);
    }

}

function  fillComboBoxInner(combobox, data, property, insideproperty, valueFactory,selected) {

    var prompt = combobox.firstChild;
    combobox.innerHTML="";

    if(selected==null) combobox.appendChild(prompt);

    for (index in  data) {
        var option = document.createElement("option");
        option.value=JSON.stringify(data[index]);
        var displayvalue="";
        if(valueFactory==null) {
            if(insideproperty==null)
                displayvalue = data[index][property];
            else
                displayvalue = data[index][property][insideproperty];
        }
        else
        {  displayvalue=valueFactory(data[index]); }
        var text = document.createTextNode(displayvalue);


        if(displayvalue==selected) {
            option.selected = "selected";
            combobox.style.background = valid;
        }

        option.appendChild(text);
        combobox.appendChild(option);
    }

}

/**
 * @description create given element
 * @param name: Name of the element want create
 * @param id: id of the element
 * @default id=null
 * **/
function createElement(name, id) {
    id = (typeof id !== 'undefined') ? id:null;
    var ele = document.createElement(name);
    if (id != null) {
        ele.id = id;
    }
    return ele;
}
/**
 * @description create notification message by invoking setModel
 * @param type {String }  The Type of notification message ('success' or 'error' only)
 * @param message {String } The notification message contents ( only for success )
 * @param timeout {Number} The time (in seconds) to wait before the notification is auto-dismissed (only success)
 * @example success  showResult('success','Customer Saved Successfully',6);
 * @example error showResult('error');
 * @requires bootstrap 4.0.0-dist or higher
 * **/
function showResult(type,message,timeout) {
    var model = createElement('div','msgModel');
    model.classList.add('modal');
    model.classList.add('fade');
    model.setAttribute('tabindex', '-1');
    model.setAttribute('role', 'dialog');
    model.setAttribute('aria-labelledby', 'exampleModalLabel');
    model.setAttribute('aria-hidden', 'true');
    var modeldialog = document.createElement('div');
    modeldialog.classList.add('modal-dialog');
    modeldialog.classList.add('modal-dialog-centered');
    modeldialog.style.width = "300px";
    model.setAttribute('role', 'document');
    var modelcontent = document.createElement('div');
    modelcontent.classList.add('modal-content');
    modelcontent.classList.add('modal-content-msg');
    var modelbody = document.createElement('div');
    modelbody.classList.add('modal-body');
    modelbody.classList.add('text-center');
    var icon = document.createElement('i');
    icon.classList.add('fal');
    icon.classList.add('fa-check-circle');
    icon.classList.add('model-icon');
    var title = document.createElement('p');
    title.classList.add('msg-title');
    var underline  = document.createElement('hr');
    underline.classList.add('under-line');
    var msgtext = document.createElement('p');
    msgtext.classList.add('msg-text');
    var button = document.createElement('button');
    button.setAttribute('type', 'button');
    button.setAttribute('data-dismiss', 'modal');
    button.setAttribute('aria-label', 'Close');
    button.classList.add('btn');
    button.style.color = "#fff";
    if (type==='success'){
        modelbody.classList.add('model-success-gradient');
        icon.classList.add('success');
        title.innerHTML = "Success!";
        title.classList.add('success');
        msgtext.innerHTML = message;
        button.classList.add('btn-success');
        button.classList.add('btn-success-mdl');
        button.innerHTML = "Continue";
        setTimeout(function() {$('#msgModel').modal('hide');}, timeout*1000);
    }else if (type === 'error'){
        modelbody.classList.add('model-error-gradient');
        icon.classList.add('error');
        title.innerHTML = "Oooops!";
        title.classList.add('error');
        msgtext.innerHTML = "Something went wrong try again.";
        button.classList.add('btn-danger');
        button.classList.add('btn-error-mdl');
        button.innerHTML = "Try Again";
    }else {
        console.log('showModel.type Invalid ',type);
    }
    modelbody.appendChild(icon);
    modelbody.appendChild(title);
    modelbody.appendChild(underline);
    modelbody.appendChild(msgtext);
    modelbody.appendChild(button);
    modelcontent.appendChild(modelbody);
    modeldialog.appendChild(modelcontent);
    model.appendChild(modeldialog);
    document.documentElement.appendChild(model);
    $('#msgModel').modal('show');
    $( "#msgModel" ).on('hidden.bs.modal', function(){document.getElementById("msgModel").remove();});
}

/**
 * @description Convert digits into words
 * @param s: digit
 * **/
function toWords(s) {

    var th = ['', 'thousand', 'million', 'billion', 'trillion'];
    var dg = ['zero', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine'];
    var tn = ['ten', 'eleven', 'twelve', 'thirteen', 'fourteen', 'fifteen', 'sixteen', 'seventeen', 'eighteen', 'nineteen'];
    var tw = ['twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

    s = s.toString();
    s = s.replace(/[\, ]/g, '');
    if (s != parseFloat(s)) return 'not a number';
    var x = s.indexOf('.');
    if (x == -1)
        x = s.length;
    if (x > 15)
        return 'too big';
    var n = s.split('');
    var str = '';
    var sk = 0;
    for (var i = 0; i < x; i++) {
        if ((x - i) % 3 == 2) {
            if (n[i] == '1') {
                str += tn[Number(n[i + 1])] + ' ';
                i++;
                sk = 1;
            } else if (n[i] != 0) {
                str += tw[n[i] - 2] + ' ';
                sk = 1;
            }
        } else if (n[i] != 0) { // 0235
            str += dg[n[i]] + ' ';
            if ((x - i) % 3 == 0) str += 'hundred ';
            sk = 1;
        }
        if ((x - i) % 3 == 1) {
            if (sk)
                str += th[(x - i - 1) / 3] + ' ';
            sk = 0;
        }
    }

    if (x != s.length) {
        var y = s.length;
        str += 'point ';
        for (var i = x + 1; i < y; i++)
            str += dg[n[i]] + ' ';
    }
    return str.replace(/\s+/g, ' ') + " only";
}


/**Disable Particular Option in Combo**/
/**@description disable given option in combo
 *@param selectId:  combo id
 *@param optionIndex: index of the option
 * **/
function optionDisable(selectId, optionIndex) {
    document.getElementById(selectId).children[optionIndex].disabled = "disabled";
}


/**Create Shipment status view**/

/**Date :  2019/09/29 | Time : 0046**/
/***
 * parent    =
 *
 *
 *
 *
 * */


function shipmentStatusView(parent, shipmentID, placedDate, clientname, ItemList, Status) {
    var parent = document.getElementById(parent);

    var card = document.createElement('div');
    card.classList.add('shp-card');

    var cardheader = document.createElement('div');
    cardheader.classList.add('card-header');
    cardheader.style.backgroundColor = "rgb(238,238,238)";
    cardheader.style.height = "60px";
    cardheader.style.paddingTop = "7px";

    var headerrow1 = document.createElement('div');
    headerrow1.classList.add('row');

    /**Shipment-ID**/
    var headercol1 = document.createElement('div');
    headercol1.classList.add('col-md-2');
    headercol1.style.marginRight = '-30px';
    /**Shipment Id Title**/
    var idtitle = document.createElement('p');
    idtitle.classList.add('shp-id-title');
    idtitle.innerHTML = "Shipment ID";
    /**Shipment ID value**/
    var idvalue = document.createElement('h6');
    idvalue.classList.add('shp-id-value');
    idvalue.innerHTML = shipmentID;
    headercol1.appendChild(idtitle);
    headercol1.appendChild(idvalue);

    /**Shipment Placed Date**/
    var headercol2 = document.createElement('div');
    headercol2.classList.add('col-md-2');
    headercol2.style.marginRight = '-30px';
    headercol2.style.borderLeft = "1px solid rgb(204,204,204)";
    /**Shipment Placed Date Title**/
    var placeddatetitle = document.createElement('p');
    placeddatetitle.classList.add('shp-id-title');
    placeddatetitle.innerHTML = "Placed Date";
    placeddatetitle.style.marginLeft = "10px";
    /**Shipment Placed Date value**/
    var placedatevalue = document.createElement('h6');
    placedatevalue.classList.add('shp-id-value');
    placedatevalue.innerHTML = placedDate;
    placedatevalue.style.marginLeft = "10px";
    headercol2.appendChild(placeddatetitle);
    headercol2.appendChild(placedatevalue);

    /**Shipment Client name**/
    var headercol3 = document.createElement('div');
    headercol3.classList.add('col-md-6');
    headercol3.style.marginRight = '-30px';
    headercol3.style.borderLeft = "1px solid rgb(204,204,204)";
    /**Shipment Client Name Title**/
    var clientnametitle = document.createElement('p');
    clientnametitle.classList.add('shp-id-title');
    clientnametitle.innerHTML = "Client Name";
    /**Shipment Client Name value**/
    var clientnamevalue = document.createElement('h6');
    clientnamevalue.classList.add('shp-id-value');
    clientnamevalue.innerHTML = clientname;
    headercol3.appendChild(clientnametitle);
    headercol3.appendChild(clientnamevalue);

    /**Button View shipment Detail**/
    var headercol4 = document.createElement('div');
    headercol4.classList.add('col-md');
    headercol4.classList.add('text-right');
    var btnViewDetail = document.createElement('button');
    btnViewDetail.type = 'button';
    btnViewDetail.classList.add('btn');
    btnViewDetail.classList.add('btn-sm');
    btnViewDetail.classList.add('btn-view-detail');
    btnViewDetail.innerHTML = "View Shipment Detail";
    /**To Do CREATE OnClick Function Lunch Model**/
    headercol4.appendChild(btnViewDetail);

    headerrow1.appendChild(headercol1);
    headerrow1.appendChild(headercol2);
    headerrow1.appendChild(headercol3);
    headerrow1.appendChild(headercol4);

    cardheader.appendChild(headerrow1);
    /**Card Header End***/

    /**Card Body Start**/
    var cardbody = document.createElement('div');
    cardbody.classList.add('card-body');
    cardbody.classList.add('shp-cardbody');
    var bodyrow1 = document.createElement('div');
    bodyrow1.classList.add('row');
    var bodyrow1col1 = document.createElement('div');
    bodyrow1col1.classList.add('col-md-11');
    /**Shipment Item List**/
    for (item = 0; item < ItemList.length; item++) {
        var itemname = document.createElement('h6');
        itemname.innerHTML = ItemList[item].description + " - " + ItemList[item].qty + " " + ItemList[item].packagetypeId.name;
        bodyrow1col1.appendChild(itemname);
    }
    /**Shipment Progress Bar**/
    var progressbararea = document.createElement('div');
    progressbararea.classList.add('progress');
    progressbararea.classList.add('shp-progressbar');
    var progressbar = document.createElement('div');
    progressbar.classList.add('progress-bar');
    progressbar.classList.add('bg-success');
    progressbar.classList.add('progress-bar-animated');
    progressbar.setAttribute('role', 'progressbar');
    progressbar.setAttribute('role', 'progressbar');
    progressbar.setAttribute('aria-valuemin', '0');
    progressbar.setAttribute('aria-valuemax', '100');
    if (Status.id <= 6) {
        progressbar.setAttribute('aria-valuenow', Status.id * 12.5);
        progressbar.style.width = Status.id * 12.5 + '%';
    } else if (Status.id == 7 || Status.id == 8) {
        progressbar.setAttribute('aria-valuenow', '85%');
        progressbar.style.width = '85%';
    } else if (Status.id == 9) {
        progressbar.setAttribute('aria-valuenow', '100%');
        progressbar.style.width = '100%';
    } else if (Status.id == 10) {
        progressbar.setAttribute('aria-valuenow', '100%');
        progressbar.style.width = '100%';
        progressbar.classList.add('bg-danger');
    }
    var bodyrow1col2 = document.createElement('div');
    bodyrow1col2.classList.add('col-md-1');
    var icon = document.createElement('i');
    icon.classList.add('fal');
    icon.classList.add('shp-icon');
    icon.classList.add('fa-truck-loading');
    icon.style.position = 'absolute';
    icon.style.bottom = '0';
    icon.style.marginLeft = "10px";
    //icon.setAttribute('bottom','0')
    bodyrow1col2.appendChild(icon);

    var bodyrow2 = document.createElement('div');
    bodyrow2.classList.add('row');
    var bodyrow2col1 = document.createElement('div');
    var bodyrow2col2 = document.createElement('div');
    bodyrow2col1.classList.add('col-md-6');
    bodyrow2col2.classList.add('col-md-6');

    var currentstatustitle = document.createElement('p');
    currentstatustitle.classList.add('shp-id-title');
    currentstatustitle.innerHTML = "Current Status";
    currentstatustitle.style.marginTop = "10px";
    var currentstatusvalue = document.createElement('p');
    currentstatusvalue.classList.add('shp-id-value');
    currentstatusvalue.innerHTML = Status.name;
    currentstatusvalue.style.marginTop = "-5px";
    bodyrow2col1.appendChild(currentstatustitle);
    bodyrow2col1.appendChild(currentstatusvalue);

    progressbararea.appendChild(progressbar);
    bodyrow1col1.appendChild(progressbararea);
    bodyrow1.appendChild(bodyrow1col1);
    bodyrow1.appendChild(bodyrow1col2);
    bodyrow2.appendChild(bodyrow2col1);
    bodyrow2.appendChild(bodyrow2col2);
    cardbody.appendChild(bodyrow1);
    cardbody.appendChild(bodyrow2);

    card.appendChild(cardheader);
    card.appendChild(cardbody);
    parent.appendChild(card);
}

/**--Create-Text-Field--*/
/***
 * parent      = tag want to append text field
 * id          = ID of the text field
 * lblText     = text that appears in the label
 * placeholder = text that appear in placeholder
 * def         = set default value to the text field
 * pattern     = regex patter for validate text field
 * ob          =
 * prop        =
 * oldob       =
 */

function txtField(parent, id, lblText, placeholder, def, pattern, ob, prop, oldob, required,validtext,invlidtext,updatetext) {

    var parent = document.getElementById(parent);
    var message = createElement('p',id+"message");
    message.style.fontSize = "12px"
    message.style.marginBottom = "-10px"
    var formGroup = createElement('div', id + 'FormGroup');
    formGroup.classList.add('wrapinput');

    var field = createElement('input', id);
    field.type = 'text';
    field.value = def;
    field.setAttribute('data-pattern', pattern);
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);
    field.style.height = '38px';

    if (lblText != false) {
        var label = createElement('label', id + 'Label');
        label.for = id;
        label.classList.add('txtinput-label');
        if (required == true) {
            field.setAttribute('required', true);
            label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
        } else {
            label.innerHTML = lblText;
        }
    }
    field.placeholder = placeholder;
    field.classList.add('txtinput');
    field.classList.add('form-control');
    if (lblText != false) {
        formGroup.appendChild(label);
    }
    formGroup.appendChild(field);
    formGroup.appendChild(message);
    parent.appendChild(formGroup);

    field.onkeyup = function () {
        var ob = window[this.getAttribute('data-object')];
        var oldob = window[this.getAttribute('data-oldobject')];
        var pattern = new RegExp(this.getAttribute('data-pattern'));
        var formGroup = this.parentNode.parentNode;

        //formGroup.appendChild(message);
        var val = this.value.trim();
        if (pattern.test(val)) {
            ob[prop] = val;
            if (oldob != null && oldob[prop] != ob[prop]) {
               if (updatetext != null){
                  // message.innerHTML = updatetext;
                   message.style.color = '#ffc107';
               }
                field.style.border = '1px solid #ffc107';  //update
                field.style.color = '#ffc107';
                field.style.boxShadow = 'none';
            } else {
                if (validtext != null){
                   // message.innerHTML = validtext;
                    message.style.color = "#28a745"
                }
                field.style.border = '1px solid #28a745';  //valid
                field.style.color = '#282c2f';
                field.style.boxShadow = 'none';
            }
        } else {

            if (invlidtext != null){
               /// message.innerHTML = invlidtext;
                message.style.color = "#dc3545"
            }
            field.style.border = '1px solid #dc3545';  //invalid
            field.style.color = '#dc3545';
            field.style.boxShadow = 'none';
            ob[prop] = null;
        }
    };

}

function txtSearchField(parent, id, lblText, placeholder, def, pattern, ob, prop, oldob, required, btnId, tooltip) {

    var parent = document.getElementById(parent);
    var formGroup = createElement('div', id + 'FormGroup');
    formGroup.classList.add('wrapinput');
    var formrow = createElement('div', id + 'FormRow');
    formrow.classList.add('row');
    var fieldcol = createElement('div', id + 'FieldCol');
    fieldcol.classList.add('col-md-10');
    fieldcol.style.paddingRight = '0px'
    var btncol = createElement('div', id + 'BtnCol');
    btncol.classList.add('col-md-2');
    var field = createElement('input', id);
    field.type = 'text';
    field.value = def;
    field.setAttribute('data-pattern', pattern);
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);
    field.style.height = '38px';
    field.placeholder = placeholder;
    field.classList.add('txtinput');
    field.classList.add('form-control');
    if (lblText != false) {
        var label = createElement('label', id + 'Label');
        label.innerHTML = lblText;
        label.for = id;
        label.classList.add('txtinput-label');
        if (required == true) {
            field.setAttribute('required', true);
            label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
        }
    } else {
        if (required == true) {
            field.setAttribute('required', true);
        }
    }
    $(document).ready(function () {
        $('body').tooltip({
            selector: "[data-tooltip=tooltip]",
            container: "body"
        });
    });

    var button = createElement('button', btnId);
    button.classList.add('btn');
    button.classList.add('btn-sm');
    button.classList.add('btn-secondary');
    button.classList.add('txtinput-with-button-button');
    button.setAttribute('type', 'button');
    button.setAttribute('data-tooltip', "tooltip");
    button.setAttribute('data-placement', "top");
    button.setAttribute('title', tooltip);
    button.innerHTML = '<i class="far fa-search"></i>';
    button.onclick = function () {
        $(document).ready(function () {
            $('[data-tooltip="tooltip"]').tooltip('hide')
        });
    };
    fieldcol.appendChild(field);
    btncol.appendChild(button);
    formrow.appendChild(fieldcol);
    formrow.appendChild(btncol);
    if (lblText != false) {
        formGroup.appendChild(label);
    }
    formGroup.appendChild(formrow);
    parent.appendChild(formGroup);

    field.onchange = function () {
        var ob = window[this.getAttribute('data-object')];
        var oldob = window[this.getAttribute('data-oldobject')];
        var formGroup = this.parentNode.parentNode;
        ob[prop] = JSON.parse(this.value);
        if (oldob != null && oldob[prop].id != ob[prop].id) {
            // formGroup.classList.remove('invalid');
            // formGroup.classList.remove('valid');
            //formGroup.classList.add('updated');
            field.style.border = '1px solid #ffc107';  //update
            field.style.boxShadow = 'none';
        } else {
            // formGroup.classList.remove('updated');
            // formGroup.classList.remove('invalid');
            // formGroup.classList.add('valid');
            field.style.border = '1px solid #28a745';  //valid
            field.style.boxShadow = 'none';
        }
    };

}



function pswField(parent, id, lblText, placeholder, def, pattern, ob, prop, oldob, required) {

    var parent = document.getElementById(parent);

    var formGroup = createElement('div', id + 'FormGroup');
    formGroup.classList.add('wrapinput');

    var label = createElement('label', id + 'Label');
    label.innerHTML = lblText;
    label.for = id;
    label.classList.add('txtinput-label');


    var field = createElement('input', id);
    field.type = 'password';
    field.value = def;
    field.setAttribute('data-pattern', pattern);
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);
    field.placeholder = placeholder;
    field.classList.add('txtinput');
    field.classList.add('form-control');

    if (required == true) {
        field.setAttribute('required', true);
        label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
    }

    formGroup.appendChild(label);
    formGroup.appendChild(field);
    parent.appendChild(formGroup);

    field.onkeyup = function () {
        var ob = window[this.getAttribute('data-object')];
        var oldob = window[this.getAttribute('data-oldobject')];
        var pattern = new RegExp(this.getAttribute('data-pattern'));
        var formGroup = this.parentNode.parentNode;
        var val = this.value.trim();
        if (pattern.test(val)) {
            ob[prop] = val;
            if (oldob != null && oldob[prop] != ob[prop]) {
                field.style.border = '1px solid #ffc107';  //update
                field.style.boxShadow = 'none';
            } else {
                field.style.border = '1px solid #28a745';  //valid
                field.style.boxShadow = 'none';
            }
        } else {
            field.style.border = '1px solid #dc3545';  //invalid
            field.style.boxShadow = 'none';
            ob[prop] = null;
        }
    };

}

/*--Create-Combo-Box--*/

/**
* parent     = tag want to append combo box
* id         = ID of combo box
* lblText    = text that appears in the label
* ob         =
* prop       =
* oldob      =
*/

function fillCmb(combo, message, list, attribute, selectedvalue) {

    combo.innerHTML = "";
    if (message != "") {
        var prompt = document.createElement("option");
        prompt.selected = "selected";
        prompt.disabled = "disabled";
        var prompttext = document.createTextNode(message);
        prompt.appendChild(prompttext);
        combo.appendChild(prompt);
    }
    for (index in list) {
        var option = document.createElement("option");
        option.value = JSON.stringify(list[index]);
        var text = document.createTextNode(list[index][attribute]);
        if (list[index][attribute] == selectedvalue) {
            option.selected = "selected";
            // combo.parentNode.parentNode.classList.add("valid");
            // combo.parentNode.parentNode.classList.remove("invalid");
            // combo.parentNode.parentNode.classList.remove("updated");
            combo.style.borderColor = valid;
        }
        

        option.appendChild(text);
        combo.appendChild(option);
    }

}

function cmbBox(parent, id, lblText, ob, prop, oldob, required) {

    var parent = document.getElementById(parent);

    var formGroup = createElement('div', id + 'FormGroup');
    formGroup.classList.add('wrapinput');

    var field = createElement('select', id);
    field.classList.add('txtinput');
    field.classList.add('form-control')
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);

    if (lblText != false) {
        var label = createElement('label', id + 'Label');
        label.innerHTML = lblText;
        label.for = id;
        label.classList.add('txtinput-label');
        if (required == true) {
            field.setAttribute('required', true);
            label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
        }
    } else {
        if (required == true) {
            field.setAttribute('required', true);
        }
    }

    if (lblText != false) {
        formGroup.appendChild(label);
    }
    formGroup.appendChild(field);
    parent.appendChild(formGroup);

    field.onchange = function () {
        var ob = window[this.getAttribute('data-object')];
        var oldob = window[this.getAttribute('data-oldobject')];
        var formGroup = this.parentNode.parentNode;
        ob[prop] = JSON.parse(this.value);
        if (oldob != null && oldob[prop].id != ob[prop].id) {
            // formGroup.classList.remove('invalid');
            // formGroup.classList.remove('valid');
            //formGroup.classList.add('updated');
            field.style.border = '1px solid #ffc107';  //update
            field.style.boxShadow = 'none';
        } else {
            // formGroup.classList.remove('updated');
            // formGroup.classList.remove('invalid');
            // formGroup.classList.add('valid');
            field.style.border = '1px solid #28a745';  //valid
            field.style.boxShadow = 'none';
        }
    };

}


function fillCombo2(combo,message,list,attribute,selectedvalue,attribute2=null) {

    combo.innerHTML="";
    if(message!="") {
        var prompt = document.createElement("option");
        prompt.selected = "selected";
        prompt.disabled = "disabled";
        var prompttext = document.createTextNode(message);
        prompt.appendChild(prompttext);
        combo.appendChild(prompt);
    }

    for (index in  list) {
        var option = document.createElement("option");
        option.value=JSON.stringify(list[index]);
        if(attribute2==null)
            var text = document.createTextNode(list[index][attribute]);
        else
            var text = document.createTextNode(list[index][attribute]+" ("+list[index][attribute2]+")");
        if(list[index][attribute]==selectedvalue) {
            option.selected = "selected";
            // combo.parentNode.parentNode.classList.add("valid");
            // combo.parentNode.parentNode.classList.remove("invalid");
            // combo.parentNode.parentNode.classList.remove("updated");
            combo.style.background = valid;
        }

        option.appendChild(text);
        combo.appendChild(option);
    }

}



function cmbBoxWithAdd(parent, id, lblText, ob, prop, oldob, required, btnId, tooltip, modelid) {

    var parent = document.getElementById(parent);

    var formGroup = createElement('div', id + 'FormGroup');
    formGroup.classList.add('wrapinput');

    /*  var label =createElement('label',id+'Label');
      label.innerHTML = lblText;
      label.for=id;
      label.classList.add('txtinput-label');*/

    var formrow = createElement('div', id + 'FormRow');
    formrow.classList.add('row');

    var fieldcol = createElement('div', id + 'FieldCol');
    fieldcol.classList.add('col-md-10');
    fieldcol.style.paddingRight = '0px'
    //fieldcol.style.paddingLeft = '15px'

    var btncol = createElement('div', id + 'BtnCol');
    btncol.classList.add('col-md-2');
    // btncol.style.paddingRight = '15px'

    var field = createElement('select', id);
    field.classList.add('txtinput');
    field.classList.add('form-control');
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);

    if (lblText != false) {
        var label = createElement('label', id + 'Label');
        label.innerHTML = lblText;
        label.for = id;
        label.classList.add('txtinput-label');
        if (required == true) {
            field.setAttribute('required', true);
            label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
        }
    } else {
        if (required == true) {
            field.setAttribute('required', true);
        }
    }

    $(document).ready(function () {
        $('body').tooltip({
            selector: "[data-tooltip=tooltip]",
            container: "body"
        });
    });

    /*
    <span data-toggle="modal" data-target="#id">
      <a data-toggle="tooltip" data-placement="top" title="My Tooltip text!">+</a>
    </span>
    * */

    var button = createElement('button', btnId);
    button.classList.add('btn');
    button.classList.add('btn-sm');
    button.classList.add('btn-secondary');
    button.classList.add('txtinput-with-button-button');
    button.setAttribute('type', 'button');
    button.setAttribute('data-tooltip', "tooltip");
    button.setAttribute('data-toggle', "modal");
    button.setAttribute('data-placement', "top");
    button.setAttribute('title', tooltip);
    button.setAttribute('data-target', modelid);
    button.innerHTML = '<i class="fas fa-plus"></i>';
    // button.style.marginRight = '15px'
    button.onclick = function () {

        /*  $(document).ready(function() {
              $('body').tooltip('hide');
          });*/

        $(document).ready(function () {
            $('[data-tooltip="tooltip"]').tooltip('hide')
        });

    };

    /*  if (required==true){
          field.setAttribute('required',true);
          label.innerHTML = lblText+"   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
      }
  */
    fieldcol.appendChild(field);
    btncol.appendChild(button);
    formrow.appendChild(fieldcol);
    formrow.appendChild(btncol);
    if (lblText != false) {
        formGroup.appendChild(label);
    }
    formGroup.appendChild(formrow);
    parent.appendChild(formGroup);

    field.onchange = function () {
        var ob = window[this.getAttribute('data-object')];
        var oldob = window[this.getAttribute('data-oldobject')];
        var formGroup = this.parentNode.parentNode;
        ob[prop] = JSON.parse(this.value);
        if (oldob != null && oldob[prop].id != ob[prop].id) {
            // formGroup.classList.remove('invalid');
            // formGroup.classList.remove('valid');
            //formGroup.classList.add('updated');
            field.style.border = '1px solid #ffc107';  //update
            field.style.boxShadow = 'none';
        } else {
            // formGroup.classList.remove('updated');
            // formGroup.classList.remove('invalid');
            // formGroup.classList.add('valid');
            field.style.border = '1px solid #28a745';  //valid
            field.style.boxShadow = 'none';
        }
    };

}

/*--Create-Date-Picker--*/

/**
* parent  =
* id      =
* lblText =
* def     =
* pattern =
* ob      =
* prop    =
* oldob   =
*/

function dteTimeField(parent, id, lblText, def, pattern, ob, prop, oldob, required) {

    var parent = document.getElementById(parent);

    var formGroup = createElement('div', id + 'FormGroup');
    formGroup.classList.add('wrapinput');

    var label = createElement('label', id + 'Label');
    label.innerHTML = lblText;
    label.for = id;
    label.classList.add('txtinput-label');


    var field = createElement('input', id);
    field.type = 'time';
    field.value = def;
    field.classList.add('txtinput');
    field.classList.add('form-control');
    field.setAttribute('data-pattern', pattern);
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);

    if (required == true) {
        field.setAttribute('required', true);
        label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
    }

    formGroup.appendChild(label);
    formGroup.appendChild(field);
    parent.appendChild(formGroup);

    field.onchange = function () {
        var ob = window[this.getAttribute('data-object')];
        var oldob = window[this.getAttribute('data-oldobject')];
        var pattern = new RegExp(this.getAttribute('data-pattern'));
        var formGroup = this.parentNode.parentNode;
        var val = this.value.trim();

        ob[prop] = val;
        if (oldob != null && oldob[prop] != ob[prop]) {
            // formGroup.classList.remove('invalid');
            // formGroup.classList.remove('valid');
            // formGroup.classList.add('updated');
            field.style.border = '1px solid #ffc107';  //update
            field.style.boxShadow = 'none';
        } else {
            // formGroup.classList.remove('updated');
            // formGroup.classList.remove('invalid');
            // formGroup.classList.add('valid');
            field.style.border = '1px solid #28a745';  //valid
            field.style.boxShadow = 'none';
        }
    }

}

function dteField(parent, id, lblText, def, pattern, ob, prop, oldob, required) {

    var parent = document.getElementById(parent);

    var formGroup = createElement('div', id + 'FormGroup');
    formGroup.classList.add('wrapinput');

    var label = createElement('label', id + 'Label');
    label.innerHTML = lblText;
    label.for = id;
    label.classList.add('txtinput-label');


    var field = createElement('input', id);
    field.type = 'Date';
    field.value = def;
    field.classList.add('txtinput');
    field.classList.add('form-control');
    field.setAttribute('data-pattern', pattern);
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);

    if (required == true) {
        field.setAttribute('required', true);
        label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
    }

    formGroup.appendChild(label);
    formGroup.appendChild(field);
    parent.appendChild(formGroup);

    field.onchange = function () {
        var ob = window[this.getAttribute('data-object')];
        var oldob = window[this.getAttribute('data-oldobject')];
        var pattern = new RegExp(this.getAttribute('data-pattern'));
        var formGroup = this.parentNode.parentNode;
        var val = this.value.trim();

        ob[prop] = val;
        if (oldob != null && oldob[prop] != ob[prop]) {
            // formGroup.classList.remove('invalid');
            // formGroup.classList.remove('valid');
            // formGroup.classList.add('updated');
            field.style.border = '1px solid #ffc107';  //update
            field.style.boxShadow = 'none';
        } else {
            // formGroup.classList.remove('updated');
            // formGroup.classList.remove('invalid');
            // formGroup.classList.add('valid');
            field.style.border = '1px solid #28a745';  //valid
            field.style.boxShadow = 'none';
        }
    }

}

/*--Create-Text-Area--*/

/**
* parent      =
* id          =
* lblText     =
* placeholder =
* def         =
* pattern     =
* ob          =
* prop        =
* oldob       =
*/

function txtArea(parent, id, lblText, placeholder, def, pattern, ob, prop, oldob, required,validtext,invlidtext,updatetext) {

    var parent = document.getElementById(parent);
    var message = createElement('p',id+"message");
    message.style.fontSize = "12px"
    message.style.marginBottom = "-10px"

    var formGroup = createElement('div', id + 'FormGroup');
    formGroup.classList.add('wrapinput');

    var label = createElement('label', id + 'Label');
    label.innerHTML = lblText;
    label.for = id;
    label.classList.add('txtinput-label');


    var field = createElement('textarea', id);
    field.innerHTML = def;
    field.classList.add('txtinput');
    field.classList.add('form-control');
    field.setAttribute('data-pattern', pattern);
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);
    field.style.height = '38px';
    field.placeholder = placeholder;

    if (required == true) {
        field.setAttribute('required', true);
        label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
    }

    formGroup.appendChild(label);
    formGroup.appendChild(field);
    formGroup.appendChild(message);
    parent.appendChild(formGroup);

    field.onkeyup = function () {
        var ob = window[this.getAttribute('data-object')];
        var oldob = window[this.getAttribute('data-oldobject')];
        var pattern = new RegExp(this.getAttribute('data-pattern'));
        var formGroup = this.parentNode.parentNode;
        var val = this.value.trim();
        if (pattern.test(val)) {
            ob[prop] = val;
            if (oldob != null && oldob[prop] != ob[prop]) {
                message.innerHTML = updatetext;
                message.style.color = '#ffc107';
                field.style.border = '1px solid #ffc107';  //update
                field.style.borderColor = "#ffc107";
                field.style.boxShadow = 'none';
            } else {
                message.innerHTML = validtext;
                message.style.color = "#28a745"
                field.style.border = '1px solid #28a745';  //valid
                field.style.borderColor = "#28a745";
                field.style.boxShadow = 'none';
            }
        } else {
            message.innerHTML = invlidtext;
            message.style.color = "#dc3545"
            field.style.border = "1px solid #dc3545";  //invalid
            field.style.borderColor = "#dc3545";
            field.style.boxShadow = 'none';
            field.style.borderColor = invalid;
            ob[prop] = null;
        }
    };


}

function photopicker() {

}

//filChooser

function filChooser(parent, id, extenstions, maxsize, required, ob, prop, oldob, required) {
    var parent = document.getElementById(parent);
    var formGroup = createElement('div', id + 'FormGroup');
    // var label = createElement('label',id+'Label');
    formGroup.classList.add('fileField');
    //label.innerHTML = lblText;
    // label.for = id;
    //   label.classList.add('control-label');
    //label.classList.add('col-md-'+labelWidth);
    var fieldArea = createElement('div');
    //fieldArea.classList.add('col-md-'+(12-labelWidth));
    var field = createElement('input', id);
    field.type = 'file';
    field.accept = extenstions.toString();
    field.style.display = 'none';
    field.setAttribute('data-object', ob);
    field.setAttribute('data-oldobject', oldob);
    field.setAttribute('data-property', prop);
    field.setAttribute('data-maxsize', maxsize);
    field.setAttribute('data-required', required);
    field.classList.add('form-control');
    //field.setAttribute('onchange','drawFile(this)');    //this draw photo
    if (required == true) {
        field.setAttribute('required', true);
        label.innerHTML = lblText + "   <i class=\"fas fa-asterisk\" style='color: #ff6666;font-size: 10px'></i>";
    }

    fieldArea.appendChild(field);


    fieldButton = createElement('fieldButton');
    fieldButton.type = 'button';
    fieldButton.classList.add('btn');
    fieldButton.classList.add('btn-sm');
    fieldButton.classList.add('btn-block');
    fieldButton.classList.add('btn-default');
    fieldButton.innerHTML = 'Select a Photo';

    fieldButton.setAttribute('onclick', 'document.getElementById(\'' + id + '\').click()');

    fieldArea.appendChild(fieldButton);


    fieldButtonC = createElement('fieldButton');
    fieldButtonC.type = 'button';
    fieldButtonC.classList.add('btn');
    fieldButtonC.classList.add('btn-sm');
    fieldButtonC.classList.add('btn-block');
    fieldButtonC.classList.add('btn-default');
    fieldButtonC.innerHTML = 'Clear ';

    fieldButtonC.onclick = function () {
        window[ob][prop] = null;
        var fileBoxes = formGroup.getElementsByClassName('file-box');
        for (var i = 0; i < fileBoxes.length; i++) {
            var box = fileBoxes[i];
            box.parentNode.removeChild(box);
        }
    };

    fieldArea.appendChild(fieldButtonC);

    formGroup.classList.add('form-group');
    //formGroup.appendChild(label);
    formGroup.appendChild(fieldArea);
    parent.appendChild(formGroup);

}

/*function listTrasferWithCombo(parent,id,lblText,ob,prop,oldob,required,btnId,tooltip,modelid,list,cmbAtr) {

    var parent = document.getElementById(parent);

    var element = createElement('div',id+'LTWC');
    cmbBoxWithAdd(id+'LTWC',id,lblText,ob,prop,oldob,required,btnId,tooltip,modelid);
    fillCmb(id,lblText+" select",list,cmbAtr,"");
}*/


/**Draw Chart**/
/**
 * @description create charts ( line, doughnut, pie, bar, horizontalBar, line)
 * @param parent: id of the canvas tag
 * @param type: type of the chart want to create ( line, doughnut, pie, bar, horizontalBar, line)
 * @param data: format {label,value}
 * @requires  Chart.js  https://www.chartjs.org/
 *
* */
function drawChart(parent,type,data) {
    var colors = ['#FF6384','#36A2EB','#FFCE56','#4BC0C0',
                  '#9966FF','#FF9F40','#FF5252','#90CAF9',
                  '#FFB300','#1DE9B6','#7B1FA2','#EF6C00',
                  '#B2FF59','#0D47A1','#F76c6c','#FFC400',
                  '#651FFF','#A7FFEB','#FF5722','#00BCD4' ];

    var primerycolor = '#FF6347';

    var dataArray = [];
    var labelArray = [];

    Chart.defaults.global.animation.duration = 2000;

    for (var x= 0;x<data.length;x++){
        dataArray.push(data[x].value);
        labelArray.push(data[x].label);
    }

    var option;
    var bgcolor;
    var brcolor;

    if (type==='pie' || type==='doughnut'){
        bgcolor = colors;
        brcolor = colors;
        option = {};
    }else if (type==='bar' || type === 'line'){
        if (type==='line'){
            bgcolor = 'transparent';
            brcolor = primerycolor;
        }else {
            bgcolor = colors;
            brcolor = colors;
        }
        option = {
             legend: {display: false},//hide label
                   scales: {
                       yAxes: [{
                           ticks: {
                               beginAtZero: true,
                               userCallback: function(label, index, labels) {
                                   if (Math.floor(label) === label) {
                                       return label;
                                   }}}}]}
        }
    }else if (type==='horizontalBar'){
        bgcolor = colors;
        brcolor = colors;
        option = {
            legend: {display: false},//hide label
            scales: {
                xAxes: [{
                    ticks: {
                        beginAtZero: true,
                        userCallback: function(label, index, labels) {
                            if (Math.floor(label) === label) {
                                return label;
                            }}}}]}
        }

    }

    var ctx = document.getElementById(parent).getContext('2d');
    var myChrt = new Chart(ctx,{type: type, data:{legend: {display: false,}, labels:labelArray, datasets: [{data: dataArray, backgroundColor:bgcolor, borderColor:brcolor, borderWidth: 1}]}, options : option})
}

function geoChart() {
    
}


/**
 * @description get screenshot of given html tag and save as a pdf file
 * @param tagId: ID of the tag want to capture
 * @param FileName: file name for the generated pdf file
 * @requires jsPDF: https://parall.ax/products/jspdf documentation for jsPDF:http://www.rotisedapsales.com/snr/cloud2/website/jsPDF-master/docs/jspdf.js.html
 * @requires html2canvas: https://html2canvas.hertzen.com/
 * **/

function genPDFofTag(tagId,FileName,pageTitle,date) {
    //console.log("pdf");
    var element = document.getElementById(tagId);
    var height = element.offsetHeight;
    var width = element.offsetWidth;

    console.log(height);
    console.log(width);

    html2canvas($('#'+tagId).get(0)).then(

        function (canvas) {
            //console.log(canvas);
            var img = canvas.toDataURL();
            var doc = new jsPDF();

            doc.setFontSize(10);
            doc.setFont('helvetica');
            doc.setFontType('bold');
            doc.text(15, 15, 'Wajira Enterprises');

            doc.setFontSize(10);
            doc.setFont('helvetica');
            doc.setFontType('bold');
            doc.text(165, 15,'Date: '+ date);

            doc.line(5,17,205,17);

            doc.setFontSize(15);
            doc.text(25,35,pageTitle);

            doc.addImage(img,'JPEG', 15, 40);
            doc.save(FileName+'.pdf');

        });

    /*            html2canvas($("#invoiceDoc"),{
                    onrendered: function (canvas) {
                        var img = canvas.toDataURL();
                        var doc = new jsPDF();
                        doc.addImage(img,'JPEG',20,20);
                        doc.save('invoice.pdf');
                    }
                })
                console.log("pdf2");*/
}

/**
 * @description customizable table
 * @param parent
 * @param id
 * @param bootstrapClasses: array of bootstrap classes
 * @param metadata  [{name:'',property:''}]
 * @param data
 * @param numbering
 * **/
function tableForReport(parent,id,bootstrapClasses,metadata,data,numbering) {
    numbering = (typeof numbering !== 'undefined') ? numbering:false;
    var parent = document.getElementById(parent);
    var ele = document.getElementById(id);
    if (ele !== null){ele.remove();}
    var table = createElement('table',id);
    table.style.borderColor = '#4d505e';
    for (var x=0; x<bootstrapClasses.length; x++){
        table.classList.add(bootstrapClasses[x]);
    }
    var thead = createElement('thead');
    var noOfColumn;
    if (numbering === true){
        noOfColumn = metadata.length+1;
    }else if (numbering === false){
        noOfColumn = metadata.length;
    }
    var tblHeaderRow = createElement('tr');
    for ( var hcolumn=0; hcolumn<noOfColumn; hcolumn++){
        var th = createElement('th');
        th.style.fontSize = '13px';
        th.style.color = '#fff';
        th.style.backgroundColor = '#4d505e';
        th.style.borderColor = '#4d505e';
        if (numbering === true){
            if (hcolumn === 0){
                th.innerHTML = '#';
            }else {
                th.innerHTML = metadata[hcolumn-1]['name'];
            }
        }else if (numbering === false){
            th.innerHTML = metadata[hcolumn]['name'];
        }
        thead.appendChild(th);
    }
    thead.appendChild(tblHeaderRow);
    var tbody = createElement('tbody');
    for (var row=0; row<data.length;row++){
      var tr = createElement('tr');
      for (var column = 0; column<noOfColumn; column++){
          var td = createElement('td');
          td.style.borderColor = '#4d505e';
          td.style.fontSize = '13px';
          if (numbering === true){
              if (column === 0){
                  td.innerHTML = row+1;
              }else {
                  td.innerHTML =  data[row][metadata[column-1]['property']];
              }
          }else if (numbering === false) {
              td.innerHTML =  data[row][metadata[column]['property']];
          }
          tr.appendChild(td);
      }
      tbody.appendChild(tr);
    }
    table.appendChild(thead);
    table.appendChild(tbody);
    parent.appendChild(table);
}

function drawGeoChart(parent) {

    var dataset = [
        ['Country', 'Popularity'],
        ['Germany', 200],
        ['New Zealand', 300],
        ['Brazil', 400],
        ['Canada', 500],
        ['France', 600],
        ['Philippines', 700]
    ];

    google.charts.load('current', {
        'packages':['geochart'],
        // Note: you will need to get a mapsApiKey for your project.
        // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
        'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'
    });
    google.charts.setOnLoadCallback(drawRegionsMap);

    function drawRegionsMap() {
        var data = google.visualization.arrayToDataTable(dataset);

        var options = {
            colorAxis: {
                colors: ['tomato']
            }
        };

        var chart = new google.visualization.GeoChart(document.getElementById(parent));

        chart.draw(data, options);
    }

}



function createNotificationCard(parent,type,content,date,time,status,id) {

    var parent = document.getElementById(parent);
    var notification = document.createElement("div");
    notification.classList.add("card");
    if (status === 2){
        notification.classList.add("notification-card-read");
    }else if (status === 1){
        notification.classList.add("notification-card-not-read");
    }
        var notificationbody = document.createElement("div");
        notificationbody.classList.add("card-body");
        notificationbody.style.padding = "0px";
            var notficationbodyrow = document.createElement("div");
            notficationbodyrow.classList.add("row");
            notficationbodyrow.style.padding = "0px";
            notficationbodyrow.style.margin = "0px";
                var notficationbodyrowcolten = document.createElement("div");
                notficationbodyrowcolten.classList.add("col-md-10");
                notficationbodyrowcolten.style.padding = "0px";
                notficationbodyrowcolten.style.padding = "0px";
                    var notificationtitlecontainer = document.createElement("a");
                    //notificationtitlecontainer.onclick = function(){parent.loadUI('shipment','Wajira Enterprises | Shipment')}
                    var notificationtitle = document.createElement("p");
                    notificationtitle.classList.add("notification-title");
                    notificationtitle.innerHTML = type;
                    notificationtitlecontainer.appendChild(notificationtitle);
                    notficationbodyrowcolten.appendChild(notificationtitlecontainer);
                    var notificationcontent = document.createElement("div");
                    notificationcontent.classList.add("notification-content");
                    notificationcontent.innerHTML = content;
                    notficationbodyrowcolten.appendChild(notificationcontent);
                notficationbodyrow.appendChild(notficationbodyrowcolten);
                var notificationbodyrowcoltwo = document.createElement("div");
                notificationbodyrowcoltwo.classList.add("col-md-2");
                notificationbodyrowcoltwo.style.padding = "0px";
                    var icon = document.createElement("i");
                    icon.classList.add("fal");
                    if ( type === "Shipment" ){
                        notificationtitlecontainer.setAttribute('onclick', "parent.loadUI('shipment','Wajira Enterprises | Shipment')");
                        icon.classList.add("fa-pallet-alt");
                    }else if (type === "Delivery"){
                        console.log("deliverry");
                        notificationtitlecontainer.setAttribute('onclick', "parent.loadUI('delivery','Wajira Enterprises | Delivery Management')");
                        icon.classList.add("fa-dolly");
                    }else if (type === "Delivery Request"){
                        notificationtitlecontainer.setAttribute('onclick', "parent.loadUI('confirmdelivery','Wajira Enterprises | Delivery Management')");
                        icon.classList.add("fa-truck-loading");
                    }else if (type === "Delivery Confirmation"){
                        notificationtitlecontainer.setAttribute('onclick', "parent.loadUI('requestdelivery','Wajira Enterprises | Delivery Management')");
                        icon.classList.add("fa-truck-loading");
                    }
                    if (status === 2){
                        icon.classList.add("notification-icon-read");
                    }else if (status === 1){
                        icon.classList.add("notification-icon-not-read");
                    }
                    notificationbodyrowcoltwo.appendChild(icon);
                notficationbodyrow.appendChild(notificationbodyrowcoltwo);
            notificationbody.appendChild(notficationbodyrow);
        var notifficationfooter = document.createElement("div");
        notifficationfooter.classList.add("card-footer");
        notifficationfooter.classList.add("notification-footer");
            var notifficationfooterrow = document.createElement("div");
            notifficationfooterrow.classList.add("row");
            notifficationfooterrow.style.padding = "5px";
                var notifficationfooterrowcolsixone = document.createElement("div");
                notifficationfooterrowcolsixone.classList.add("col-md-6");
                    var atag = document.createElement("a");
                        var link =  document.createElement("p");
                        link.innerHTML = "Mark as Read";
                        if (status === 2){
                            link.classList.add("footer-link-disabled");
                        }else if ( status ===1 ){
                            link.classList.add("footer-link");
                            atag.setAttribute('onclick', "changeNotificationStatus("+id+")");
                        }
                        atag.appendChild(link);
                    notifficationfooterrowcolsixone.appendChild(atag);
                notifficationfooterrow.appendChild(notifficationfooterrowcolsixone);
                var notifficationfooterrowcolsixtwo = document.createElement("div");
                notifficationfooterrowcolsixtwo.classList.add("col-md-6");
                    var notificationdatetime = document.createElement("p");
                    notificationdatetime.classList.add("text-right");
                    notificationdatetime.classList.add("footer-text");
                    notificationdatetime.innerHTML = date+" - "+time;
                    notifficationfooterrowcolsixtwo.appendChild(notificationdatetime);
                notifficationfooterrow.appendChild(notifficationfooterrowcolsixtwo);
            notifficationfooter.appendChild(notifficationfooterrow);
    notification.appendChild(notificationbody);
    notification.appendChild(notifficationfooter);
    parent.appendChild(notification);
}

function createStickyNoteCard(parent,notetitle,notecontent,notedate,backgroundColor) {
    var parent  = document.getElementById(parent);
    const backgroundcolor = tinycolor(backgroundColor);
    var textcolor;
    if (backgroundcolor.isLight()){
        textcolor = "#000"
    }else if (backgroundcolor.isDark()){
        textcolor = "#fff"
    }

    let card = document.createElement("div");
    card.classList.add("card");
    card.style.marginBottom = "5px";

    let cardbody = document.createElement("div");
    cardbody.classList.add("card-body");
    cardbody.style.border = "1px solid black";
    cardbody.style.padding ="10px 10px 2px 10px";
    cardbody.style.backgroundColor = backgroundColor;

    let row = document.createElement("div");
    row.classList.add("row");

    let col11 = document.createElement("div");
    col11.classList.add("col-md-11");

    let title = document.createElement("p");
    title.classList.add("sticky-note-title");
    title.style.color = textcolor;
    title.innerHTML = notetitle;
    col11.appendChild(title);

    let col1 = document.createElement("div");
    col1.classList.add("col-md-1");
    col1.style.padding = "0";
    col1.style.margin = "0";

    let deleteButton = document.createElement("button");
    deleteButton.classList.add("btn");
    deleteButton.classList.add("sticky-note-delete-button");
    deleteButton.setAttribute("type","button");
    deleteButton.setAttribute("data-toggle","tooltip");
    deleteButton.setAttribute("data-placement","bottom");
    deleteButton.setAttribute("title","Delete Sticky note");
    deleteButton.setAttribute("onclick","btndeleteStickyNote()");
    deleteButton.style.color = textcolor;
    let deleteButtonIcon = document.createElement("i");
    deleteButtonIcon.classList.add("far");
    deleteButtonIcon.classList.add("fa-times");
    deleteButton.appendChild(deleteButtonIcon);
    col1.appendChild(deleteButton);

    row.appendChild(col11);
    row.appendChild(col1);

    let content = document.createElement("p");
    content.classList.add("sticky-note-content");
    content.style.color = textcolor;
    content.innerHTML = notecontent;

    let date = document.createElement("p");
    date.classList.add("sticky-note-date");
    date.classList.add("text-right");
    date.style.color = textcolor;
    date.innerHTML = notedate;


    cardbody.appendChild(row);
    cardbody.appendChild(content);
    cardbody.appendChild(date);
    card.appendChild(cardbody);
    parent.appendChild(card);


}
