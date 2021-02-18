--drop table product_stock;
--drop table product_io;
--drop sequence seq_io_no;

--상품 테이블 생성
create table product_stock(
    product_id varchar2(30),
    product_name varchar2(30) not null,
    price number(10) not null,
    description varchar2(50),
    stock number default 0,
    constraint pk_product_stock primary key (product_id)
);

--상품입출고 테이블 생성
create table product_io(
    io_no number,
    product_id varchar2(30),
    iodate date default sysdate,
    amount number,
    status char(1) check(status in ('I', 'O')),
    constraint pk_io_no primary key (io_no),
    constraint fk_product_id foreign key (product_id)
                             references product_stock (product_id)
);

--시퀀스 생성
create sequence seq_io_no
start with 1
increment by 1;

--트리거 생성
--drop trigger trg_product;
create or  replace trigger trg_product
    before
    insert on product_io
    for each row
begin
    --입고
    if :new.status = 'I' then
        update product_stock
        set stock = stock + :new.amount
        where product_id = :new.product_id;
    --출고
    else
        update product_stock
        set stock = stock - :new.amount
        where product_id = :new.product_id;    
    end if;
end;
/

select * from product_stock;
select * from product_io;
select * from user_sequences;

insert into product_stock
values('1', '사과', 4000, '마시쪙', 0);

insert into product_io
values(seq_io_no.nextval, '1', sysdate, 50, 'I');

commit;




