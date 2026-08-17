const apiUrl = '/api/bookings';
const form = document.getElementById('bookingForm');
const message = document.getElementById('message');
const bookingsContainer = document.getElementById('bookings');
const refreshButton = document.getElementById('refreshButton');

form.addEventListener('submit', async (event) => {
    event.preventDefault();
    message.textContent = 'Sparar bokningen...';

    const booking = {
        customerName: document.getElementById('customerName').value.trim(),
        customerEmail: document.getElementById('customerEmail').value.trim(),
        serviceName: document.getElementById('serviceName').value,
        appointmentTime: document.getElementById('appointmentTime').value
    };

    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(booking)
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(formatError(error));
        }

        form.reset();
        message.textContent = 'Bokningen är bekräftad.';
        await loadBookings();
    } catch (error) {
        message.textContent = error.message || 'Bokningen kunde inte sparas.';
    }
});

refreshButton.addEventListener('click', loadBookings);

async function loadBookings() {
    bookingsContainer.innerHTML = '<p class="empty">Laddar bokningar...</p>';

    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error('Kunde inte hämta bokningar.');
        const bookings = await response.json();
        renderBookings(bookings);
    } catch (error) {
        bookingsContainer.innerHTML = `<p class="empty">${escapeHtml(error.message)}</p>`;
    }
}

function renderBookings(bookings) {
    if (bookings.length === 0) {
        bookingsContainer.innerHTML = '<p class="empty">Du har inga bokningar ännu.</p>';
        return;
    }

    bookingsContainer.innerHTML = bookings.map(booking => `
        <article class="booking-item">
            <div>
                <h3>${escapeHtml(booking.serviceName)}</h3>
                <p>${escapeHtml(booking.customerName)} · ${escapeHtml(booking.customerEmail)}</p>
                <p>${formatDate(booking.appointmentTime)}</p>
                <span class="status">${escapeHtml(booking.status)}</span>
            </div>
            ${booking.status === 'CONFIRMED'
                ? `<button class="danger" onclick="cancelBooking(${booking.id})">Avboka</button>`
                : ''}
        </article>
    `).join('');
}

async function cancelBooking(id) {
    const response = await fetch(`${apiUrl}/${id}/cancel`, { method: 'PATCH' });
    if (response.ok) await loadBookings();
}

function formatDate(value) {
    return new Intl.DateTimeFormat('sv-SE', {
        dateStyle: 'medium',
        timeStyle: 'short'
    }).format(new Date(value));
}

function formatError(error) {
    if (error.errors) return Object.values(error.errors).join(' · ');
    return error.message || 'Kontrollera uppgifterna och försök igen.';
}

function escapeHtml(value) {
    return String(value ?? '')
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}

loadBookings();
