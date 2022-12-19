<#import "blocks/template.ftlh" as t>
<@t.template user>
  <div class="container">
    <div class="d-flex flex-wrap  justify-content-lg-start">
      <div class="col-4">
        <span class="fs-4 fw-semibold align-items-center" style="color: black">BALANCE: ${cashAccount.balance} BYN</span>
        <br>
        <button type="button" class="btn btn-warning me-2" onclick="window.location.href = '/user/cashAccount/deposit'">Deposit</button>
      </div>
      <div class="col-4">
        <#list deposits as deposit>
          <div class="list-group w-auto" style="height: 6rem";>
              <div class="d-flex gap-2 w-100 justify-content-between" >
                    <div>
                      <h6 class="mb-0">Deposit via card: ${deposit.getCardNumber()}</h6>
                      <p class="mb-0 opacity-75">Replenishment in the amount of ${deposit.getSum()} BYN</p>
                        <small class="opacity-50 text-nowrap">${deposit.getDateOfCreated()}</small>
                    </div>
              </div>
          </div>
            <hr>
        </#list>
      </div>
        <div class="col-4" style="padding-left: 10px;">
            <#list payments as payment>
                <div class="list-group w-auto" style="height: 6rem;">
                    <div class="d-flex gap-2 w-100 justify-content-between">
                        <div>
                            <h6 class="mb-0">Payment for product billing: ${payment.getProduct().getTitle()}</h6>
                            <p class="mb-0 opacity-75">Payment amount -${payment.getSum()} BYN</p>
                            <small class="opacity-50 text-nowrap">${payment.getDateOfCreated()}</small>
                        </div>
                    </div>
                </div>
                <hr>
            </#list>
        </div>
    </div>
  </div>

</@t.template>