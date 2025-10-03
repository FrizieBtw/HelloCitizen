document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll("[data-view]");
    const contentDiv = document.getElementById("content");

    buttons.forEach(btn => {
        btn.addEventListener("click", () => {
            const view = btn.getAttribute("data-view");

            fetch(view)
                .then(res => res.text())
                .then(html => {
                    contentDiv.innerHTML = html;

                    if (view.includes("habitants.html")) {
                        loadResidents();
                    } else if (view.includes("habitantCreation.html")) {
                        setupCreateForm();
                    } else if (view.includes("cadeaux.html")) {
                        loadGifts();
                    } else if (view.includes("cadeauCreation.html")) {
                        setupGiftForm();
                    }
                });
        });
    });
});

function setupCreateForm(residentId = null) {
    const form = document.getElementById("createResidentForm");
    const formTitle = document.getElementById("formTitle");
    const submitButton = document.getElementById("submitButton");
    const idInput = document.getElementById("residentId");

    if (residentId) {
        formTitle.textContent = "Modifier un habitant";
        submitButton.textContent = "Modifier";

        fetch(`http://127.0.0.1:8080/residents/${residentId}`)
            .then(res => res.json())
            .then(data => {
                idInput.value = data.id;
                document.getElementById("firstName").value = data.firstName;
                document.getElementById("lastName").value = data.lastName;
                document.getElementById("birthday").value = data.birthday;
                document.getElementById("email").value = data.email || "";
                document.getElementById("number").value = data.number || "";
                document.getElementById("address").value = data.address || "";
                document.getElementById("arrivalDate").value = data.arrivalDate;
                document.getElementById("notificationDate").value = data.notificationDate || "";
            });
    } else {
        formTitle.textContent = "Créer un habitant";
        submitButton.textContent = "Créer";
        form.reset();
        if (idInput) {
            if (residentId) {
                idInput.value = residentId;
            } else {
                idInput.value = "";
            }
        }
    }

    form.onsubmit = (e) => {
        e.preventDefault();

        const firstName = document.getElementById("firstName").value.trim();
        const lastName = document.getElementById("lastName").value.trim();
        const birthday = document.getElementById("birthday").value;
        const arrivalDate = document.getElementById("arrivalDate").value;

        if (!firstName || !lastName || !birthday || !arrivalDate) {
            alert("Veuillez remplir tous les champs obligatoires : prénom, nom, date de naissance et date d'arrivée.");
            return;
        }

        const resident = {
            firstName,
            lastName,
            birthday,
            email: document.getElementById("email").value,
            number: document.getElementById("number").value,
            address: document.getElementById("address").value,
            arrivalDate,
            notificationDate: document.getElementById("notificationDate").value || null
        };

        const id = idInput.value;
        const method = id ? "PUT" : "POST";
        const url = id ? `http://127.0.0.1:8080/residents/${id}` : "http://127.0.0.1:8080/residents";

        fetch(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(resident)
        })
        .then(res => {
            if (!res.ok) throw new Error(`Erreur lors de ${id ? "la modification" : "la création"}`);
            return res.json();
        })
        .then(data => {
            alert(`Habitant ${id ? "modifié" : "créé"} avec succès : ${data.firstName} ${data.lastName}`);
            const contentDiv = document.getElementById("content");
            fetch("views/habitants.html")
                .then(res => res.text())
                .then(html => {
                    contentDiv.innerHTML = html;
                    loadResidents();
                });
        })
        .catch(err => alert(err.message));
    };
}

function loadResidents() {
    fetch("http://127.0.0.1:8080/residents")
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById("residents-table-body");

            if (!data.length) {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="11" class="text-center">
                            <div class="alert alert-warning mb-0">Aucun habitant trouvé.</div>
                        </td>
                    </tr>
                `;
                return;
            }

            const rows = data.map(r => `
                <tr>
                    <td>${r.id}</td>
                    <td>${r.firstName}</td>
                    <td>${r.lastName}</td>
                    <td>${r.birthday || "-"}</td>
                    <td>${r.email}</td>
                    <td>${r.number}</td>
                    <td>${r.address}</td>
                    <td>${r.arrivalDate || "-"}</td>
                    <td>${r.notificationDate || "-"}</td>
                    <td class="text-center">
                        <i class="bi bi-pencil-square text-primary cursor-pointer btn-modify" data-id="${r.id}" title="Modifier"></i>
                    </td>
                    <td class="text-center">
                        <i class="bi bi-trash text-danger cursor-pointer btn-delete" data-id="${r.id}" title="Supprimer"></i>
                    </td>
                </tr>
            `).join('');

            tbody.innerHTML = rows;

            tbody.querySelectorAll(".btn-modify").forEach(icon => {
                icon.addEventListener("click", (e) => {
                    const id = e.target.dataset.id;
                    const contentDiv = document.getElementById("content");
                    fetch("views/habitantCreation.html")
                        .then(res => res.text())
                        .then(html => {
                            contentDiv.innerHTML = html;
                            setupCreateForm(id);
                        });
                });
            });

            tbody.querySelectorAll(".btn-delete").forEach(icon => {
                icon.addEventListener("click", (e) => {
                    const id = e.target.dataset.id;
                    if (confirm(`Voulez-vous vraiment supprimer l'habitant avec ID ${id} ?`)) {
                        fetch(`http://127.0.0.1:8080/residents/${id}`, { method: "DELETE" })
                            .then(res => {
                                if (!res.ok) throw new Error("Erreur lors de la suppression");
                                loadResidents();
                            })
                            .catch(err => alert(err.message));
                    }
                });
            });
        })
        .catch(() => {
            const tbody = document.getElementById("residents-table-body");
            tbody.innerHTML = `
                <tr>
                    <td colspan="11" class="text-center">
                        <div class="alert alert-danger mb-0">Erreur de chargement</div>
                    </td>
                </tr>
            `;
        });
}

function setupGiftForm(giftId = null) {
    const form = document.getElementById("createGiftForm");
    const title = document.getElementById("formTitle");
    const submitButton = document.getElementById("submitButton");
    const giftIdInput = document.getElementById("giftId");

    if (giftId) {
        title.textContent = "Modifier un cadeau";
        submitButton.textContent = "Modifier";

        fetch(`http://localhost:8080/api/gifts/${giftId}`)
            .then(res => res.json())
            .then(data => {
                giftIdInput.value = data.id;
                document.getElementById("label").value = data.libelle;
                document.getElementById("barcode").value = data.codeBarres;
                document.getElementById("minAge").value = data.ageMin;
                document.getElementById("maxAge").value = data.ageMax;
                document.getElementById("price").value = data.price;
            });
    } else {
        title.textContent = "Créer un cadeau";
        submitButton.textContent = "Créer";
        form.reset();
        giftIdInput.value = "";
    }

    form.onsubmit = (e) => {
        e.preventDefault();

        const libelle = document.getElementById("label").value.trim();
        const codeBarres = document.getElementById("barcode").value.trim();
        const ageMin = parseInt(document.getElementById("minAge").value);
        const ageMax = parseInt(document.getElementById("maxAge").value);
        const price = parseFloat(document.getElementById("price").value) || 0;

        if (!libelle || !codeBarres || isNaN(ageMin) || isNaN(ageMax)) {
            alert("Veuillez remplir tous les champs obligatoires : libellé, code-barres, âge minimum et maximum.");
            return;
        }

        const gift = { libelle, codeBarres, ageMin, ageMax, price };
        const id = giftIdInput.value;
        const method = id ? "PUT" : "POST";
        const url = id ? `http://localhost:8080/api/gifts/${id}` : "http://localhost:8080/api/gifts";

        fetch(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(gift)
        })
        .then(res => {
            if (!res.ok) throw new Error(`Erreur lors de ${id ? "la modification" : "la création"}`);
            return res.json();
        })
        .then(data => {
            alert(`Cadeau ${id ? "modifié" : "créé"} avec succès : ${data.libelle}`);
            loadGifts();
            form.reset();
            giftIdInput.value = "";
            title.textContent = "Créer un cadeau";
            submitButton.textContent = "Créer";
        })
        .catch(err => alert(err.message));
    };
}

function loadGifts() {
    fetch("http://localhost:8080/api/gifts")
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById("gifts-table-body");

            if (!data.length) {
                tbody.innerHTML = `<tr><td colspan="8" class="text-center"><div class="alert alert-warning mb-0">Aucun cadeau trouvé.</div></td></tr>`;
                return;
            }

            tbody.innerHTML = data.map(g => `
                <tr>
                    <td>${g.id}</td>
                    <td>${g.libelle}</td>
                    <td>${g.codeBarres}</td>
                    <td>${g.ageMin}</td>
                    <td>${g.ageMax}</td>
                    <td>${g.price.toFixed(2)} €</td>
                    <td class="text-center">
                        <i class="bi bi-pencil-square text-primary cursor-pointer btn-modify" data-id="${g.id}" title="Modifier"></i>
                    </td>      
                    <td class="text-center">
                        <i class="bi bi-trash text-danger cursor-pointer btn-delete ms-2" data-id="${g.id}" title="Supprimer"></i>                    
                    </td>
                </tr>
            `).join('');

            tbody.querySelectorAll(".btn-modify").forEach(btn => {
                btn.addEventListener("click", e => setupGiftForm(e.target.dataset.id));
            });

            tbody.querySelectorAll(".btn-delete").forEach(btn => {
                btn.addEventListener("click", e => {
                    const id = e.target.dataset.id;
                    if (confirm(`Voulez-vous vraiment supprimer le cadeau avec ID ${id} ?`)) {
                        fetch(`http://localhost:8080/api/gifts/${id}`, { method: "DELETE" })
                            .then(res => {
                                if (!res.ok) throw new Error("Erreur lors de la suppression");
                                loadGifts();
                            })
                            .catch(err => alert(err.message));
                    }
                });
            });
        })
        .catch(() => {
            document.getElementById("gifts-table-body").innerHTML = `
                <tr><td colspan="8" class="text-center"><div class="alert alert-danger mb-0">Erreur de chargement</div></td></tr>`;
        });
}
