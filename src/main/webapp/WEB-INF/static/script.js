function showModal() {
    const modal = document.getElementById("createModal");
    modal.style.display = "block";
}

function closeModal() {
    const modal = document.getElementById("createModal");
    modal.style.display = "none";
}

function goto(page, host) {
    let href = host + '?page=' + page;
    console.log(href)
    window.location.href = href;
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

function handlePaginationClick(event, host) {
    const target = event.target.closest("li");
    if (target) {
        if (target.classList.contains("numb")) {
            goto(target.dataset.page, host);
        } else if (target.classList.contains("prev")) {
            goto(currentPage - 1, host);
        } else if (target.classList.contains("next")) {
            goto(currentPage + 1, host);
        }
    }
}

createPagination(totalPages, currentPage);

