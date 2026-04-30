<#import "../layout/base.ftlh" as layout>
<@layout.base cartCount=cartCount username=username!"">

    <h2 class="mb-4">Корзина</h2>

    <#if error??>
        <div class="alert alert-danger">Ошибка оформления заказа. Попробуйте снова.</div>
    </#if>

    <#if items?has_content>
        <div class="card shadow-sm">
            <div class="card-body p-0">
                <table class="table table-hover mb-0">
                    <thead class="table-dark">
                    <tr>
                        <th>Блюдо</th>
                        <th>Заведение</th>
                        <th>Кол-во</th>
                        <th>Сумма</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list items as item>
                        <tr>
                            <td>${item.dish.name}</td>
                            <td class="text-muted">${item.dish.cafe.name}</td>
                            <td>${item.quantity}</td>
                            <td class="fw-bold text-success">${item.subtotal} ₽</td>
                            <td>
                                <form method="post" action="/cart/remove/${item.dish.id}">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <button class="btn btn-sm btn-outline-danger">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="d-flex justify-content-between align-items-center mt-4">
            <h5 class="mb-0">Итого: <strong class="text-success">${total} ₽</strong></h5>
            <#if username?has_content>
                <form method="post" action="/orders/place">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary btn-lg">
                        <i class="bi bi-check2-circle"></i> Оформить заказ
                    </button>
                </form>
            <#else>
                <a href="/login" class="btn btn-warning btn-lg">
                    Войдите чтобы оформить заказ
                </a>
            </#if>
        </div>

    <#else>
        <div class="alert alert-info">
            Корзина пуста. <a href="/cafes">Перейти к заведениям</a>
        </div>
    </#if>

</@layout.base>