const BASE = "http://localhost:8080";

let companies = [];
let products = [];

let editCompanyId = null;
let editProductId = null;

// ================= INIT =================
function init() {
    checkLogin();
    loadCompanies();
    loadProducts();
}

// ================= LOGIN =================
function checkLogin() {
    if (!localStorage.getItem("admin")) {
        window.location.href = "/admin/login";
    }
}

// ================= TOGGLE =================
function toggle(id) {
    const el = document.getElementById(id);
    el.style.display = el.style.display === "none" ? "block" : "none";
}

// ================= COMPANY =================
async function loadCompanies() {
    const res = await fetch(BASE + "/api/companies");
    const result = await res.json();

    companies = result.data || [];

    const table = document.getElementById("companyTable");
    const select = document.getElementById("pcompany");

    table.innerHTML = "";
    select.innerHTML = "";

    companies.forEach(c => {
        table.innerHTML += `
            <tr>
                <td>${c.name}</td>
                <td>${c.email}</td>
                <td>${c.phone}</td>
                <td>${c.website || ''}</td>
                <td>${c.taxNumber || ''}</td>
                <td>
                    <button onclick="editCompany(${c.id})">Edit</button>
                    <button onclick="deleteCompany(${c.id})">Delete</button>
                </td>
            </tr>
        `;

        select.innerHTML += `<option value="${c.id}">${c.name}</option>`;
    });
}

// EDIT COMPANY
function editCompany(id) {
    const c = companies.find(x => x.id === id);

    cname.value = c.name;
    cemail.value = c.email;
    cphone.value = c.phone;
    caddress.value = c.address;
    cwebsite.value = c.website;
    ctax.value = c.taxNumber;

    editCompanyId = id;
    toggle('companyForm');
}

// SAVE COMPANY
async function saveCompany() {
    const data = {
        name: cname.value,
        email: cemail.value,
        phone: cphone.value,
        address: caddress.value,
        website: cwebsite.value,
        taxNumber: ctax.value
    };

    const url = editCompanyId
        ? BASE + "/api/companies/" + editCompanyId
        : BASE + "/api/companies";

    const method = editCompanyId ? "PUT" : "POST";

    const res = await fetch(url, {
        method,
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    const result = await res.json();

    if (result.success) {
        alert("Company saved ✅");
        editCompanyId = null;
        clearForm("companyForm");
        loadCompanies();
    } else {
        alert(result.message);
    }
}

// DELETE COMPANY
async function deleteCompany(id) {
    await fetch(BASE + "/api/companies/" + id, {method:"DELETE"});
    loadCompanies();
}

// ================= PRODUCT =================
async function loadProducts() {
    try {
        const res = await fetch(BASE + "/api/products");
        const result = await res.json();

        products = result.data || [];

        const table = document.getElementById("productTable");
        table.innerHTML = "";

        if (products.length === 0) {
            table.innerHTML = "<tr><td colspan='5'>No products found</td></tr>";
            return;
        }

        products.forEach(p => {
            table.innerHTML += `
                <tr>
                    <td>${p.name}</td>
                    <td>${p.price}</td>
                    <td>${p.description || ''}</td>
                    <td>${p.company?.name || 'N/A'}</td>
                    <td>
                        <button onclick="editProduct(${p.id})">Edit</button>
                        <button onclick="deleteProduct(${p.id})">Delete</button>
                    </td>
                </tr>
            `;
        });

    } catch (err) {
        console.error(err);
    }
}

// EDIT PRODUCT
function editProduct(id) {
    const p = products.find(x => x.id === id);

    pname.value = p.name;
    pprice.value = p.price;
    pdesc.value = p.description;
    pcompany.value = p.company?.id;

    editProductId = id;
    toggle('productForm');
}

// SAVE PRODUCT
async function saveProduct() {
    const data = {
        name: pname.value,
        price: pprice.value,
        description: pdesc.value,
        companyId: pcompany.value
    };

    const url = editProductId
        ? BASE + "/api/products/" + editProductId
        : BASE + "/api/products";

    const method = editProductId ? "PUT" : "POST";

    const res = await fetch(url, {
        method,
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    const result = await res.json();

    if (result.success) {
        alert("Product saved ✅");
        editProductId = null;
        clearForm("productForm");
        loadProducts();
    } else {
        alert(result.message);
    }
}

// DELETE PRODUCT
async function deleteProduct(id) {
    await fetch(BASE + "/api/products/" + id, {method:"DELETE"});
    loadProducts();
}

// ================= COMMON =================
function clearForm(formId) {
    document.querySelectorAll(`#${formId} input`).forEach(i => i.value = "");
}

function logout() {
    localStorage.removeItem("admin");
    window.location.href = "/admin/login";
}