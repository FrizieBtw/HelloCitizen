(function () {
            const params = new URLSearchParams(window.location.search);
            const residentId = params.get('id');
            if (!residentId) {
                document.getElementById('noGiftsAlert').classList.remove('d-none');
                document.getElementById('noGiftsAlert').textContent = "ID de l'habitant manquant dans l'URL (ex: ?id=123).";
                document.getElementById('attributionForm').style.display = 'none';
                return;
            }
            document.getElementById('residentId').value = residentId;

            const giftsContainer = document.getElementById('giftsContainer');
            const carouselInner = document.getElementById('carousel-inner');
            const carouselIndicators = document.getElementById('carousel-indicators');
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

            fetch(`/api/residents/${residentId}/gifts`)
                .then(res => res.json())
                .then(data => {
                    gifts = data || [];
                    if (!gifts.length) {
                        noGiftsAlert.classList.remove('d-none');
                        return;
                    }
                    renderCarousel();
                })
                .catch(() => {
                    noGiftsAlert.classList.remove('d-none');
                    noGiftsAlert.textContent = "Impossible de récupérer les cadeaux.";
                });

            function renderCarousel() {
                carouselInner.innerHTML = '';
                carouselIndicators.innerHTML = '';
                gifts.forEach((g, idx) => {
                    const ind = document.createElement('button');
                    ind.type = 'button';
                    ind.setAttribute('data-bs-target', '#giftsCarousel');
                    ind.setAttribute('data-bs-slide-to', String(idx));
                    if (idx === 0) ind.classList.add('active');
                    carouselIndicators.appendChild(ind);

                    const item = document.createElement('div');
                    item.className = 'carousel-item' + (idx === 0 ? ' active' : '');
                    item.style.padding = '1rem';
                    const card = document.createElement('div');
                    card.className = 'card mx-auto';
                    card.style.maxWidth = '500px';
                    card.style.cursor = 'pointer';
                    card.addEventListener('click', () => selectGift(g));
                    const body = document.createElement('div');
                    body.className = 'card-body text-center';
                    body.innerHTML = `<h5 class="card-title">${g.libelle}</h5>
        <p class="small text-muted mb-1">${g.codeBarres ? `Code: ${g.codeBarres}` : ''}</p>
        <p class="small text-muted">${g.ageMin != null && g.ageMax != null ? `Âge ${g.ageMin} → ${g.ageMax}` : ''}</p>`;
                    card.appendChild(body);
                    item.appendChild(card);
                    carouselInner.appendChild(item);
                });
                giftsContainer.classList.remove('d-none');
            }

            function selectGift(g) {
                selectedGiftId = g.id;
                selectedPreview.classList.remove('d-none');
                selectedLibelle.textContent = g.libelle;
                selectedBarcode.textContent = g.codeBarres ? `Code: ${g.codeBarres}` : '';
                selectedAgeRange.textContent = g.ageMin != null && g.ageMax != null ? `Âge recommandé : ${g.ageMin} — ${g.ageMax} ans` : '';
                selectedImage.innerHTML = '<i class="bi bi-gift" style="font-size:2rem;color:#0d6efd"></i>';
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
                const payload = { residentId: Number(residentId), giftId: selectedGiftId, email, deliveryAddress: address };
                fetch('/attributions', {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                })
                    .then(res => {
                        if (!res.ok) return res.text().then(t => { throw new Error(t || 'Erreur serveur'); });
                        return res.json().catch(() => ({}));
                    })
                    .then(() => {
                        formMessage.innerHTML = '<div class="alert alert-success small mb-0">Attribution enregistrée et mail envoyé.</div>';
                    })
                    .catch(err => {
                        formMessage.innerHTML = `<div class="alert alert-danger small mb-0">Erreur : ${err.message}</div>`;
                    });
            });
        })();