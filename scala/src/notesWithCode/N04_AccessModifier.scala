package notesWithCode {
    /**
     * Create by weiyupeng on 2021/8/1 15:42
     */
    class N04_AccessModifier {
        /**
         * private
         */
        class Inner {
            private def f(): Unit = {
                println("f");
            }

            def pub(): Unit = {
                println("pub");
            }

            class InnerMost {
                f(); // 正确
            }
        }
        // (new Inner).f() // 错误

        /**
         * protected
         */
        class Super {
            protected def f(): Unit = {
                println("f");
            }
        }

        class Sub extends Super {
            f(); // 正确
        }

        class Other {
            // (new Super).f(); // 错误

            /**
             * public
             */
            // 不加修饰符默认就是 public
            (new Inner).pub(); // 正确
        }
    }

    // innerPackage.N04_AccessModifier_2.scala 作用域保护
}








