let inputField = document.querySelector("#in")
let ll = document.querySelector("#ll")
let resp = document.querySelector("#resp")
var sw = new Array()

document.querySelector("#clean").onclick = () => {
    ll.innerHTML = ""
    resp.innerText = ""
    sw = new Array()
}


document.querySelector("#but").onclick = () => {
    let ae = document.createElement("li")
    ae.innerText = inputField.value
    sw.push(inputField.value)
    inputField.value = ""
    ll.appendChild(ae)
}

document.querySelector("#send").onclick = () => {
    xhr = new XMLHttpRequest();
    var url = "/search";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            resp.innerText = xhr.responseText
        }
    }
    var data = JSON.stringify({"keywords": sw});
    xhr.send(data);
}
