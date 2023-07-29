function updateSouvenir() {
    let name = document.querySelector('#uname').value;
    let price = document.querySelector('#uprice').value;
    let issue = document.querySelector('#uissue').value;
    let brand = document.querySelector('#ubrand').value;
    let country = document.querySelector('#ucountry').value;

    let url = '/souvenir' + '?name=' + name + '&price=' + price + '&issue=' + issue + '&brand=' + brand + '&country=' + country;

    fetch(url, {
        method: 'put',
    })
        .then(response => {
            console.log('Ok:', response);
            window.location.href = url;
        })
        .then(text => console.log(text));
}

function deleteSouvenir(name) {
    let url = '/souvenir' + '?name=' + name;
    fetch(url, {
        method: 'delete',
    }).then(response => {
        console.log('Ok:', response);
        window.location.href = url;
    }).catch(err => {
        console.error(err)
    })
}

function showColumn(columnsToShow) {
    const table = document.querySelector(".table-list");
    const rows = table.querySelectorAll("tr");

    rows.forEach(row => {
        const cells = row.querySelectorAll("td, th");
        cells.forEach((cell, index) => {
            if (columnsToShow.includes(index)) {
                cell.style.display = "";
            } else {
                cell.style.display = "none";
            }
        });
    });
}

function showModal() {
    const modal = document.getElementById("createModal");
    modal.style.display = "block";
}

function closeModal() {
    const modal = document.getElementById("createModal");
    modal.style.display = "none";
}

function goto(page) {
    console.log(page)
    window.location.href = '/souvenir?page=' + page;
}

const element = document.querySelector(".pagination ul");

function createPagination(totalPages, currentPage) {
    let liTag = '';
    let active;
    let beforePage = currentPage - 1;
    let afterPage = currentPage + 1;

    if (currentPage > 1) {
        liTag += '<li class="btn prev" data-page="' + (currentPage - 1) + '"><span><i class="fa fa-angle-left"></i> Prev</span></li>';
    }

    if (currentPage > 4) {
        liTag += '<li class="first numb" data-page="1"><span>1</span></li>';
        if (currentPage > 5) {
            liTag += '<li class="dots"><span>...</span></li>';
        }
    } else {
        beforePage = 1;
    }

    if (currentPage === totalPages) {
        beforePage = beforePage - 2;
    } else if (currentPage === totalPages - 1) {
        beforePage = beforePage - 1;
    }

    if (currentPage === 1) {
        afterPage = afterPage + 2;
    } else if (currentPage === 2) {
        afterPage = afterPage + 1;
    }

    if (beforePage < 1) {
        beforePage = 1;
    }

    for (let number = beforePage; number <= afterPage; number++) {
        if (number > totalPages) {
            continue;
        }
        active = currentPage === number ? "active" : "";
        liTag += '<li class="numb ' + active + '" data-page="' + number + '"><span>' + number + '</span></li>';
    }

    if (totalPages > 5) {
        if (currentPage < totalPages - 1) {
            if (currentPage < totalPages - 2) {
                liTag += '<li class="dots"><span>...</span></li>';
            }
            liTag += '<li class="last numb" data-page="' + totalPages + '"><span>' + totalPages + '</span></li>';
        }
    }

    if (currentPage < totalPages) {
        liTag += '<li class="btn next" data-page="' + (currentPage + 1) + '"><span>Next <i class="fa fa-angle-right"></i></span></li>';
    }

    element.innerHTML = liTag;
    return liTag;
}


function handlePaginationClick(event) {
    const target = event.target.closest("li");
    if (target) {
        if (target.classList.contains("numb")) {
            goto(target.dataset.page);
        } else if (target.classList.contains("prev")) {
            goto(currentPage - 1);
        } else if (target.classList.contains("next")) {
            goto(currentPage + 1);
        }
    }
}

createPagination(totalPages, currentPage);