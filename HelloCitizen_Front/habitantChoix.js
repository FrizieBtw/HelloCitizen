(function () {
    const params = new URLSearchParams(window.location.search);
    const residentId = params.get('resident');
    if (!residentId) {
        document.getElementById('noGiftsAlert').classList.remove('d-none');
        document.getElementById('noGiftsAlert').textContent = "ID de l'habitant manquant dans l'URL (ex: ?resident=123).";
        document.getElementById('attributionForm').style.display = 'none';
        return;
    }
    document.getElementById('residentId').value = residentId;

    const giftsContainer = document.getElementById('giftsContainer');
    const giftsList = document.getElementById('giftsList');
    const noGiftsAlert = document.getElementById('noGiftsAlert');
    const selectedPreview = document.getElementById('selectedPreview');
    const selectedLibelle = document.getElementById('selectedLibelle');
    const selectedBarcode = document.getElementById('selectedBarcode');
    const selectedAgeRange = document.getElementById('selectedAgeRange');
    const selectedImage = document.getElementById('selectedImage');
    const form = document.getElementById('attributionForm');
    const formMessage = document.getElementById('formMessage');

    let gifts = [];
    let selectedGiftId = null;

    fetch(`http://localhost:8080/api/residents/${residentId}/gifts`)
        .then(res => res.json())
        .then(data => {
            gifts = data || [];
            if (!gifts.length) {
                noGiftsAlert.classList.remove('d-none');
                noGiftsAlert.textContent = "Veuillez contacter la Mairie, aucun cadeau disponible pour votre tranche d'âge !";
                return;
            }
            renderGiftsList();
        })
        .catch(() => {
            noGiftsAlert.classList.remove('d-none');
            noGiftsAlert.textContent = "Impossible de récupérer les cadeaux.";
        });

    function renderGiftsList() {
        giftsList.innerHTML = '';
        gifts.forEach(g => {
            const card = document.createElement('div');
            card.className = 'card text-center';
            card.style.minWidth = '150px';
            card.style.cursor = 'pointer';
            card.addEventListener('click', () => selectGift(g));

            const imgSrc = g.image ? `data:image/png;base64,${g.image}` : '';
            const imgHTML = imgSrc
                ? `<img src="${imgSrc}" class="card-img-top" style="max-height:100px;object-fit:contain;">`
                : `<i class="bi bi-gift" style="font-size:3rem;color:#0d6efd;margin-top:1rem;"></i>`;

            card.innerHTML = `
                ${imgHTML}
                <div class="card-body p-2">
                    <h6 class="card-title">${g.libelle}</h6>
                    <p class="small text-muted mb-0">${g.codeBarres || ''}</p>
                    <p class="small text-muted mb-0">${g.ageMin != null && g.ageMax != null ? `Âge ${g.ageMin} → ${g.ageMax}` : ''}</p>
                </div>
            `;
            giftsList.appendChild(card);
        });

        giftsContainer.classList.remove('d-none');
    }

    function selectGift(g) {
        selectedGiftId = g.id;
        selectedPreview.classList.remove('d-none');
        selectedLibelle.textContent = g.libelle;
        selectedBarcode.textContent = g.codeBarres ? `Code: ${g.codeBarres}` : '';
        selectedAgeRange.textContent = g.ageMin != null && g.ageMax != null ? `Âge recommandé : ${g.ageMin} — ${g.ageMax} ans` : '';

        if (g.image) {
            selectedImage.innerHTML = `<img src="data:image/png;base64,${g.image}" style="max-width:96px;max-height:96px;border-radius:8px;">`;
        } else {
            selectedImage.innerHTML = '<i class="bi bi-gift" style="font-size:2rem;color:#0d6efd"></i>';
        }
    }

    form.addEventListener('submit', e => {
        e.preventDefault();
        form.classList.add('was-validated');
        formMessage.innerHTML = '';
        const email = document.getElementById('email').value.trim();
        const address = document.getElementById('deliveryAddress').value.trim();
        if (!email || !address) {
            formMessage.innerHTML = '<div class="text-danger small">Email et adresse requis</div>';
            return;
        }
        if (!selectedGiftId) {
            formMessage.innerHTML = '<div class="text-danger small">Sélectionnez un cadeau</div>';
            return;
        }

        const payload = {
            residentId: Number(residentId),
            giftId: selectedGiftId,
            email,
            deliveryAddress: address
        };

        fetch('http://localhost:8080/api/attributions', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
        .then(res => {
            if (!res.ok) return res.text().then(t => { throw new Error(t || 'Erreur serveur'); });
            return res.json().catch(() => ({}));
        })
        .then(() => {
            document.body.innerHTML = `
                <div class="container py-5 text-center">
                    <h2>Merci !</h2>
                    <p>Le cadeau a été attribué avec succès!</p>
                </div>
            `;
        })
        .catch(err => {
            formMessage.innerHTML = `<div class="alert alert-danger small mb-0">Erreur : ${err.message}</div>`;
        });
    });
})();
