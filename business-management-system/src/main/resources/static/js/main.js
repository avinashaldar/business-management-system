const BASE_URL = "http://localhost:8080";

async function login(url, data, redirectPage) {
    const res = await fetch(BASE_URL + url, {
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(data)
    });

    const result = await res.json();

    if(result.success){
        localStorage.setItem("admin", JSON.stringify(result.data));
        window.location.href = redirectPage;
    } else alert(result.message);
}

async function register(url, data) {
    const res = await fetch(BASE_URL + url, {
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(data)
    });

    const result = await res.json();

    if(result.success){
        alert("Registered Successfully");
        window.location.href="/admin/login";
    } else alert(result.message);
}