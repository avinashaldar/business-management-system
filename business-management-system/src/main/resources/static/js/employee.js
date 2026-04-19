const BASE = "http://localhost:8080";

let products = [];
let items = [];

// ================= INIT =================
function init() {
    checkLogin();
    loadProducts();

    document.getElementById("tax").addEventListener("input", calculateTotal);
    document.getElementById("discount").addEventListener("input", calculateTotal);
}

// ================= LOGIN =================
function checkLogin() {
    if (!localStorage.getItem("employee")) {
        window.location.href = "/employee/login";
    }
}

// ================= LOAD PRODUCTS =================
async function loadProducts() {
    const emp = JSON.parse(localStorage.getItem("employee"));

    const res = await fetch(BASE + "/api/products?companyId=" + emp.company.id);
    const result = await res.json();

    products = result.data || [];

    const select = document.getElementById("productSelect");
    select.innerHTML = "";

    products.forEach(p => {
        select.innerHTML += `<option value="${p.id}">${p.name} - ₹${p.price}</option>`;
    });
}

// ================= ADD ITEM =================
function addItem() {

    const pid = productSelect.value;
    const qty = parseInt(quantity.value);

    if (!qty || qty <= 0) return alert("Invalid quantity");

    const p = products.find(x => x.id == pid);

    const totalPrice = p.price * qty;

    items.push({
        productId: p.id,
        name: p.name,
        price: p.price,
        qty,
        totalPrice
    });

    renderItems();
    calculateTotal();
}

// ================= RENDER =================
function renderItems() {

    const table = document.getElementById("itemsTable");
    table.innerHTML = "";

    items.forEach((i, index) => {
        table.innerHTML += `
        <tr>
            <td>${i.name}</td>
            <td>${i.price}</td>
            <td>${i.qty}</td>
            <td>${i.totalPrice}</td>
            <td>
                <button onclick="removeItem(${index})">❌</button>
            </td>
        </tr>`;
    });
}

// ================= REMOVE =================
function removeItem(index) {
    items.splice(index, 1);
    renderItems();
    calculateTotal();
}

// ================= CALCULATE =================
function calculateTotal() {

    let baseTotal = 0;
    items.forEach(i => baseTotal += i.totalPrice);

    const taxPercent = parseFloat(document.getElementById("tax").value) || 0;
    const discountPercent = parseFloat(document.getElementById("discount").value) || 0;

    const taxAmount = (baseTotal * taxPercent) / 100;
    const discountAmount = (baseTotal * discountPercent) / 100;

    const net = baseTotal + taxAmount - discountAmount;

    document.getElementById("base").innerText = baseTotal.toFixed(2);
    document.getElementById("taxVal").innerText = taxAmount.toFixed(2);
    document.getElementById("discountVal").innerText = discountAmount.toFixed(2);
    document.getElementById("total").innerText = net.toFixed(2);

    return { taxAmount, discountAmount };
}

// ================= BUILD DATA =================
function buildInvoiceData() {

    const emp = JSON.parse(localStorage.getItem("employee"));
    const calc = calculateTotal();
    const payment = document.getElementById("paymentMode").value;

    if (items.length === 0) return alert("Add items first");
    if (!payment) return alert("Select payment mode");

    return {
        companyId: emp.company.id,
        taxAmount: calc.taxAmount,
        discountAmount: calc.discountAmount,
        paymentMode: payment,
        items: items.map(i => ({
            productId: i.productId,
            quantity: i.qty
        }))
    };
}

// ================= SAVE =================
async function saveInvoice() {

    const data = buildInvoiceData();
    if (!data) return;

    const res = await fetch(BASE + "/api/invoices", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    const result = await res.json();

    if (result.success) {
        alert("Invoice Saved ✅");
        location.reload();
    } else {
        alert(result.message);
    }
}

// ================= SAVE + PDF =================
async function generateInvoice() {

    const data = buildInvoiceData();
    if (!data) return;

    const res = await fetch(BASE + "/api/invoices", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    const result = await res.json();

    if (result.success) {
        alert("Invoice Generated ✅");
        window.location.href = BASE + "/api/invoices/pdf/" + result.data.id;
        location.reload();
    } else {
        alert(result.message);
    }
}

// ================= LOGOUT =================
function logout() {
    localStorage.removeItem("employee");
    window.location.href = "/employee/login";
}