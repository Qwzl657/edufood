<#import "../layout/base.ftlh" as layout>
<@layout.base cartCount=cartCount username=username!"">

    <div class="d-flex align-items-center gap-3 mb-3">
        <a href="/cafes" class="btn btn-outline-secondary btn-sm">← Назад</a>
        <h2 class="mb-0">${cafe.name}</h2>
    </div>
    <p class="text-muted">${cafe.description!""}</p>
    <hr>

    <div class="row g-3">
        <#list dishes as dish>
            <div class="col-md-6">
                <div class="card shadow-sm h-100">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="mb-1">${dish.name}</h6>
                            <small class="text-muted">${dish.description!""}</small>
                            <div class="fw-bold text-success mt-1">${dish.price} ₽</div>
                        </div>
                        <form method="post" action="/cart/add/${dish.id}">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <input type="hidden" name="redirect" value="/cafes/${cafe.id}"/>
                            <button class="btn btn-success btn-sm">
                                <i class="bi bi-plus-lg"></i> В корзину
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <div class="d-flex justify-content-center align-items-center gap-2 mt-4">
        <#if (currentPage > 0)>
            <a href="/cafes/${cafe.id}?page=${currentPage - 1}"
               class="btn btn-outline-secondary">← Назад</a>
        </#if>
        <#if (totalPages > 1)>
            <span class="btn btn-light disabled">
        ${currentPage + 1} / ${totalPages}
    </span>
        </#if>
        <#if (currentPage + 1 < totalPages)>
            <a href="/cafes/${cafe.id}?page=${currentPage + 1}"
               class="btn btn-outline-primary">Вперёд →</a>
        </#if>
    </div>

</@layout.base>