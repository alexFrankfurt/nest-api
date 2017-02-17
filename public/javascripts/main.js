let inputField = document.querySelector("#input-field")
let ll = document.querySelector("#lookup-list")
let resp = document.querySelector("#app-response")
let but = document.querySelector("#add-button")
let sendButton = document.querySelector("#send")
let cleanButton = document.querySelector("#clean")
var sw = new Array()

inputField.onkeyup = (e) => {
   if (e.key == "Enter") but.click()
}

cleanButton.onclick = () => {
    ll.innerHTML = ""
    resp.innerText = ""
    sw = new Array()
}

but.onclick = () => {
    let ae = document.createElement("li")
    ae.innerText = inputField.value
    sw.push(inputField.value)
    inputField.value = ""
    ll.appendChild(ae)
}

sendButton.onclick = () => {
    xhr = new XMLHttpRequest()
    var url = "/search"
    xhr.open("POST", url, true)
    xhr.setRequestHeader("Content-type", "application/json")
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            resp.innerText = xhr.responseText
        }
    }
    var data = JSON.stringify({"keywords": sw})
    xhr.send(data)
}

document.onkeyup = (e) => {
    if (e.ctrlKey) {
        switch (e.key) {
            case "Enter":
                sendButton.click()
                break;
            case "c":
                cleanButton.click()
                break;
        }
    }
}
