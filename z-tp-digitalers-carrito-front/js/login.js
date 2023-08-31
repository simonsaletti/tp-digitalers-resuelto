const modal = document.getElementById('modalLogin');

const login = async (event) => {
    event.preventDefault();
    // const params = new URLSearchParams();
    if (txtUsuario.value.trim() === '') {
        mostrarModal('&#128533;', 'El usuario no puede ser sólo espacios');
        return false;
    }
    // if (txtUsuario.value.length > 8)
    // params.set('username', txtUsuario.value.trim());
    // params.set('password', txtClave.value);
    // params.set('grant_type', 'password');

    const params = {
        username: txtUsuario.value.trim(),
        password: txtClave.value,
    };

    try {
        // const credenciales = 'carrito' + ':' + '12345';
        // const credencialesEncriptaoBase64 = btoa(credenciales);
        const response = await fetch('http://localhost:8080/token', {
            method: 'POST',
            body: JSON.stringify(params),
            headers: {
                'Content-Type': 'application/json',
                // Authorization: 'Basic ' + credencialesEncriptaoBase64,
            },
        });
        const data = await response.json();
        console.log(data);
        switch (response.status) {
            case 200:
                mostrarModal('&#128512', 'Sesión iniciada con éxito');
                // alert('Sesión iniciada con éxito.');
                guardarTokenSS(data.access_token);
                // window.location.replace('./sesionok.html');
                modal.querySelectorAll('button').forEach((boton) => {
                    boton.addEventListener('click', () => {
                        window.location.replace('./index.html');
                    });
                });
                // window.location.replace('./index.html');
                break;
            default:
                mostrarModal('&#128533;', 'Error');
                // new bootstrap.Modal(document.getElementById('modalLogin'), {}).show();
                // alert('Errooooooor');
                break;
        }
    } catch (error) {
        mostrarModal('&#128533;', 'El servidor no está disponible (catch general)');
    }
};

const mostrarModal = (carita, mensaje) => {
    modal.querySelector('#loginTitulo').innerHTML = carita;
    modal.querySelector('.modal-body').innerHTML = mensaje + '.';
    new bootstrap.Modal(document.getElementById('modalLogin'), {}).show();
};

document.querySelector('form').addEventListener('submit', login);

const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#txtClave');

togglePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});

tokenPresente();
