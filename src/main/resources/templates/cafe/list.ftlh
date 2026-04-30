<#import "../layout/base.ftlh" as layout>
<@layout.base cartCount=cartCount username=username!"">

    <h2 class="mb-4">Заведения</h2>

    <form method="get" action="/cafes" class="d-flex gap-2 mb-4">
        <input type="text" name="search" value="${search}"
               class="form-control" placeholder="Поиск заведения..."/>
        <button class="btn btn-primary">Найти</button>
        <#if search?has_content>
            <a href="/cafes" class="btn btn-outline-secondary">Сбросить</a>
        </#if>
    </form>

    <div class="row g-4">
        <#list cafes as cafe>
            <div class="col-md-6">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${cafe.name}</h5>
                        <p class="card-text text-muted">${cafe.description!""}</p>
                        <a href="/cafes/${cafe.id}" class="btn btn-outline-primary">
                            Смотреть меню →
                        </a>
                    </div>
                </div>
            </div>
        <#else>
            <div class="col-12">
                <div class="alert alert-info">Заведения не найдены</div>
            </div>
        </#list>
    </div>

    <div class="d-flex justify-content-center align-items-center gap-2 mt-4">
        <#if (currentPage > 0)>
            <a href="/cafes?page=${currentPage - 1}&search=${search}"
               class="btn btn-outline-secondary">← Назад</a>
        </#if>
        <span class="btn btn-light disabled">
        ${currentPage + 1} / ${totalPages}
    </span>
        <#if (currentPage + 1 < totalPages)>
            <a href="/cafes?page=${currentPage + 1}&search=${search}"
               class="btn btn-outline-primary">Вперёд →</a>
        </#if>
    </div>

</@layout.base>