function showModal() {
    const modal = document.getElementById("createModal");
    modal.style.display = "block";
}

function closeModal() {
    const modal = document.getElementById("createModal");
    modal.style.display = "none";
}

function goto(page) {
    window.location.href = '/?page=' + page;
}

const element = document.querySelector(".pagination ul");
element.innerHTML = createPagination(totalPages, page);

function createPagination(totalPages, page) {
    let liTag = '';
    let active;
    let beforePage = page - 1;
    let afterPage = page + 1;
    if (page > 1) {
        liTag += `<li class="btn prev" onclick="createPagination(totalPages, ${page - 1})"><span><i class="fa fa-angle-left"></i> Prev</span></li>`;
    }

    if (page > 2) {
        liTag += `<li class="first numb" onclick="createPagination(totalPages, 1)"><span>1</span></li>`;
        if (page > 3) {
            liTag += `<li class="dots"><span>...</span></li>`;
        }
    }

    if (page === totalPages) {
        beforePage = beforePage - 2;
    } else if (page === totalPages - 1) {
        beforePage = beforePage - 1;
    }

    if (page === 1) {
        afterPage = afterPage + 2;
    } else if (page === 2) {
        afterPage = afterPage + 1;
    }

    for (let number = beforePage; number <= afterPage; number++) {
        if (number > totalPages) {
            continue;
        }
        if (number === 0) {
            number = number + 1;
        }
        if (page === number) {
            active = "active";
        } else {
            active = "";
        }
        liTag += `<li class="numb ${active}" onclick="createPagination(totalPages, ${number})"><span onclick="goto(${number})">${number}</span></li>`;
    }

    if (page < totalPages - 1) {
        if (page < totalPages - 2) {
            liTag += `<li class="dots"><span>...</span></li>`;
        }
        liTag += `<li class="last numb" onclick="createPagination(totalPages, ${totalPages})"><span>${totalPages}</span></li>`;
    }

    if (page < totalPages) {
        liTag += `<li class="btn next" onclick="createPagination(totalPages, ${page + 1})"><span>Next <i class="fa fa-angle-right"></i></span></li>`;
    }
    element.innerHTML = liTag;
    return liTag;
}